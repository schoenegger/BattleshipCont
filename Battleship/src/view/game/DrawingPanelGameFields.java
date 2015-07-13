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

		createFieldView(ownField);
		createFieldView(enemyField);
		// super.revalidate();
		// super.repaint();
	}

	private void createFieldView(Field gameField)
	{
		double width = 60;
		double height = 60;
		double x = 30;
		double y = 30;

		for (int i = 0; i < 10; i++)
		{
			// change coloum
			for (int j = 0; j < 10; j++)
			{

				// draw line
				graphic.drawString("PanelWidht" + this.getWidth(), 15, 15);

				graphic.draw(new Rectangle2D.Double(x, y, width, height));

				// graphic.drawImage(image, (int) (x + (width / 2 - 25)),
				// (int) (y + (height / 2 - 25)), this);

				x += width;
			}
			x = 30;
			y += height;
		}
	}
}
