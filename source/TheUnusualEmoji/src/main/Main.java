package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {

		if (args.length == 0) return;

		System.out.println("本ソフトは「源柔ゴシックBold」を使用しています。 (http://jikasei.me/font/genjyuu/) .\r\n" +
				"Licensed under SIL Open Font License 1.1 (http://scripts.sil.org/OFL)\r\n" +
				"© 2015 自家製フォント工房, © 2014, 2015 Adobe Systems Incorporated, © 2015 M+\r\n" +
				"FONTS PROJECT" +
				"\r\n");

		String NameOffset = "";
		if (args.length > 1) NameOffset = args[1];

		String emojimsg = args[0];
		Font font = null;

		int PicW, PicH;
		try {
			BufferedImage base = ImageIO.read(new File("empty.png"));
			PicW = base.getWidth();
			PicH = base.getWidth();
		} catch (IOException e) {
			System.out.println("[ERROR] 元画像が見つかりません");
			return;
		}

		if (PicH != PicW) {
			System.out.println("[WARN] 元画像が正方形ではありません。絵文字にする都合上、正方形にすることをお勧めします。");
		}
		if (PicH < 64 | PicW < 64) {
			System.out.println("[WARN] 元画像のサイズが小さいです");
		}
		if (PicH < 8 | PicW < 8) {
			System.out.println("[ERROR] 元画像のサイズが小さすぎます");
		}
		float fontsize;
		if (PicH > PicW) {
			fontsize = PicW*185/256;
		} else {
			fontsize = PicH*185/256;
		}


		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("GenJyuuGothic-Bold.ttf")).deriveFont(fontsize);
		} catch (FontFormatException e) {
			System.out.println("[ERROR] フォントファイルが破損しています");
			return;
		} catch (IOException e) {
			System.out.println("[ERROR] フォントファイルが見つかりません");
			return;
		}

		BufferedImage origin = null;
		for (char moji : emojimsg.toCharArray()) {
			try {
				if (NameOffset != "") {
					if (new File(moji + "-" + NameOffset + ".png").exists()) continue;
				} else {
					if (new File(moji + ".png").exists()) continue;
				}
			} catch (Exception e) {
				System.out.println("[ERROR] ファイルの存在確認に失敗しました。不正な文字列がファイル名に使用されている可能性があります。");
				return;
			}

			try {
				origin = ImageIO.read(new File("empty.png"));
			} catch (IOException e) {
				System.out.println("[ERROR] 元画像が見つかりません");
				return;
			}
			Graphics grap = origin.getGraphics();

			grap.setColor(Color.WHITE);
			grap.setFont(font);
			drawStrCenter(grap, String.valueOf(moji), PicW/2, PicH/2);
			try {
				if (NameOffset != "") {
					ImageIO.write(origin, "png", new File(moji + "-" + NameOffset + ".png"));
				} else {
					ImageIO.write(origin, "png", new File(moji + ".png"));
				}
			} catch (IOException e) {
				System.out.println("[ERROR] 不明なエラーが発生しました。詳細は以下を確認してください。");
				e.printStackTrace();
				return;
			}
			System.out.println("絵文字 「 " + moji + " 」 を作成しました");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}


	}

	public static void drawStrCenter(Graphics g, String text, int x, int y) {
		// 引用 from: http://techhot.hatenablog.com/entry/drawstringcentermiddle_1
		FontMetrics fm = g.getFontMetrics();
		Rectangle rectText = fm.getStringBounds(text, g).getBounds();
		x=x-rectText.width/2;
		y=y-rectText.height/2+fm.getMaxAscent();
		g.drawString(text, x, y);
	}
}
