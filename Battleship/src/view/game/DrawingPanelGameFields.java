package view.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameUtilities.Field.Field;

public class DrawingPanelGameFields extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Graphics2D graphic;
	private BufferedImage image;
	private Field ownField;
	private Field enemyField;

	public DrawingPanelGameFields(Field ownField, Field enemyField)
	{
		this.ownField = ownField;
		this.enemyField = enemyField;
	}

	@Override
	public void paintComponent(Graphics graph)
	{
		// try
		// {
		// image = ImageIO.read(new File("ship.png"));
		// }
		// catch (IOException e)
		// {
		// System.out.println("Fuck you 'Image");
		// e.printStackTrace();
		// }
		graphic = (Graphics2D) graph.create();

		createFieldView(ownField, (1.0 / 12.0), (1.0 / 6.0), (1.0 / 30.0));
		createFieldView(enemyField, (7.0 / 12.0), (1.0 / 6.0), (1.0 / 30.0));
		// super.revalidate();
		// super.repaint();
	}

	private void createFieldView(Field gameField, double factorStartX,
			double factorStartY, double factorRect)
	{
		// double width = 60;
		// double height = 60;
		// double x = 30;
		// double y = 30;

		double widthReck = this.getWidth() * factorRect;
		double heightReck = widthReck;
		double startX = this.getWidth() * factorStartX;
		double startY = this.getHeight() * factorStartY;

		graphic.drawString("PanelWidht" + this.getWidth(), (int) startX,
				(int) startY - 20);

		for (int i = 0; i < 10; i++)
		{
			// change coloum
			for (int j = 0; j < 10; j++)
			{

				// draw line
				// graphic.drawString("PanelWidht" + this.getWidth(), 20,
				// 20);

				graphic.draw(new Rectangle2D.Double(startX, startY, widthReck,
						heightReck));

				// graphic.drawImage(image, (int) (x + (width / 2 - 25)),
				// (int) (y + (height / 2 - 25)), this);

				startX += widthReck;
			}
			startX = this.getWidth() * factorStartX;
			startY += heightReck;
		}
	}
}
