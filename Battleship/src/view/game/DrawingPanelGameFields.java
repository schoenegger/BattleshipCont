package view.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GameUtilities.ShipType;
import GameUtilities.Field.Field;
import GameUtilities.Field.FieldElement;
import GameUtilities.Field.FieldState;

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
	private int mouseCourserX = 0;
	private int mouseCourserY = 0;
	private ShipType currShipType = ShipType.AIRCARRIER;
	private String align = "horizontal";
	private int currMousePosX = 5;
	private int currMousePosY = 5;

	private final Color ATTAC_COLOR = Color.red;
	private final Color NOT_VALID_COLOR = Color.gray;
	private final Color VALID_COLOR = Color.green;
	private final Color DEFAULT_FOREGROUND = new Color(0, 0, 255);
	private final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 20);

	public DrawingPanelGameFields(Field ownField, Field enemyField)
	{
		setBackground(new Color(169, 169, 169));
		this.ownField = ownField;
		this.enemyField = enemyField;
		startRunningText(10);
		// this.setBackground(new Color(255, 255, 255));

		this.setForeground(DEFAULT_FOREGROUND);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.repaint();
		this.revalidate();
	}

	private void startRunningText(int i)
	{
		// graphic.drawString(
	}

	public void setMouseCourser(int x, int y, ShipType type, String align)
	{
		mouseCourserX = x;
		mouseCourserY = y;
		currShipType = type;
		this.align = align;

	}

	public String getCurrentMousePosition()
	{
		return currMousePosX + "," + currMousePosY;
	}

	@Override
	public void paintComponent(Graphics graph)
	{

		graphic = (Graphics2D) graph.create();

		createFieldView(ownField, (1.0 / 12.0), (1.0 / 6.0), (1.0 / 30.0),
				"OWN FIELD", true);
		createFieldView(enemyField, (7.0 / 12.0), (1.0 / 6.0), (1.0 / 30.0),
				"ENEMY FIELD", false);

	}

	private void createFieldView(Field gameField, double factorStartX,
			double factorStartY, double factorRect, String fieldName,
			boolean isOwmField)
	{

		double widthReck = this.getWidth() * factorRect;
		double heightReck = widthReck;
		double startX = this.getWidth() * factorStartX;
		double startY = this.getHeight() * factorStartY;

		graphic.setPaint(DEFAULT_FOREGROUND);
		graphic.drawString(fieldName, (int) startX, (int) startY - 20);

<<<<<<< HEAD
		for (int i = 0; i < 10; i++)
		{
			// change coloum
			for (int j = 0; j < 10; j++)
			{
				if (courserIsInField(startX, startY, widthReck, heightReck))
				{
					currMousePosX = i;
					currMousePosY = j;

					gameField.setPossibleFields(i, j, currShipType, align);

				}
				// ***If own ship in INIT MODE****************
				if (gameField.getFieldElement(i, j).isOwn()
						&& gameField.getFieldElement(i, j)
								.isAvailableToSetShip())
=======
		for (int line = 0; line < 10; line++)
		{
			// change coloum
			for (int coloum = 0; coloum < 10; coloum++)
			{
				if (courserIsInField(startX, startY, widthReck, heightReck))
				{
					currMousePosX = line;
					currMousePosY = coloum;

					gameField.setPossibleFields(line, coloum, currShipType,
							align);

				}

				// ***If own ship in INIT MODE****************

				if (gameField.getFieldElement(line, coloum).isOwn()
						&& gameField.getFieldElement(line, coloum)
								.isAvailableToSetShip())

>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
				{

					if (gameField.isShipSettingPossiple())
					{
						drawFilledRectangle(widthReck, heightReck, startX,
								startY, VALID_COLOR);
					}
					else
					{
						drawFilledRectangle(widthReck, heightReck, startX,
								startY, NOT_VALID_COLOR);
					}
				}
				// ******************************************

<<<<<<< HEAD
				else if ((!gameField.getFieldElement(i, j).isOwn())
						&& (ownField.isFieldInit()))
				{
					if (gameField.getFieldElement(i, j).isAvailableToAttac())
					{
=======
				// **************It Game is in Attac mode**********

				else if ((!gameField.getFieldElement(line, coloum).isOwn())
						&& (ownField.isFieldInit()))
				{
					if (gameField.getFieldElement(line, coloum)
							.isAvailableToAttac())
					{

>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
						drawFilledRectangle(widthReck, heightReck, startX,
								startY, ATTAC_COLOR);
					}
					else
					{
						graphic.setPaint(DEFAULT_FOREGROUND);
						graphic.draw(new Rectangle2D.Double(startX, startY,
								widthReck, heightReck));
					}
<<<<<<< HEAD
					// else
					// {
					// drawFilledRectangle(widthReck, heightReck, startX,
					// startY, NOT_VALID_COLOR);
					// }

				}
=======
				}

>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
				else
				{
					graphic.setPaint(DEFAULT_FOREGROUND);
					graphic.draw(new Rectangle2D.Double(startX, startY,
							widthReck, heightReck));
				}

				BufferedImage image = getImageByFildType(gameField
<<<<<<< HEAD
						.getFieldElement(i, j));
=======
						.getFieldElement(line, coloum));
>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3

				if (image != null)
				{
					// 25 = half of iconWidth
					graphic.drawImage(image,
							(int) (startX + (widthReck / 2 - 25)),
							(int) (startY + (heightReck / 2 - 25)), this);
				}

				startX += widthReck;
			}
			startX = this.getWidth() * factorStartX;
			startY += heightReck;
		}
		gameField.unmarkFields();
	}

	private void drawFilledRectangle(double widthReck, double heightReck,
			double startX, double startY, Color color)
	{
		graphic.setPaint(color);
		graphic.fill(new Rectangle2D.Double(startX, startY, widthReck,
				heightReck));
	}

	private boolean courserIsInField(double startX, double startY,
			double widthReck, double heightReck)
	{
		boolean isInField = true;
		isInField &= ((mouseCourserX > (int) startX) && (mouseCourserX < (int) (startX + widthReck)));
		isInField &= ((mouseCourserY > (int) startY) && (mouseCourserY < (int) (startY + heightReck)));
		return isInField;
	}

<<<<<<< HEAD
	private boolean setElementPositionByMouseCouser(Field gameField)
	{

		return false;
	}

=======
>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
	private BufferedImage getImageByFildType(FieldElement fieldElement)
	{
		BufferedImage image = null;
		String path = "";

<<<<<<< HEAD
		if (fieldElement.isTaken())
		{
			if (fieldElement.getFieldState() == FieldState.STRIKE_SHIP)
			{
				path = "src\\img\\ship_dest.png";// strike ship
			}
			else
			{
				path = "src\\img\\ship.png";
			}
		}

		if (!fieldElement.isTaken())
		{
			if (fieldElement.getFieldState() == FieldState.UNKNOWN
					&& !fieldElement.isOwn())
=======
		if (fieldElement.isOwn())
		{
			if (fieldElement.isTaken())
			{
				if (fieldElement.getFieldState() == FieldState.STRIKE_SHIP)
				{
					path = "src\\img\\ship_dest.png";// strike ship
				}
				else
				{
					path = "src\\img\\ship.png";
				}
			}
			if (!fieldElement.isTaken())
			{
				if (fieldElement.getFieldState() == FieldState.UNKNOWN)
				{

					path = "src\\img\\questm.png";
				}
				else
				{
					path = "src\\img\\wave.png";
				}
			}
		}
		if (!fieldElement.isOwn())
		{
			if (fieldElement.getFieldState() == FieldState.UNKNOWN)
>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
			{

				path = "src\\img\\questm.png";
			}
<<<<<<< HEAD
=======
			else if (fieldElement.getFieldState() == FieldState.STRIKE_SHIP)
			{
				path = "src\\img\\ship_dest.png";
			}
>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
			else
			{
				path = "src\\img\\wave.png";
			}
		}
<<<<<<< HEAD

=======
>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
		try
		{
			image = ImageIO.read(new File(path));
		}
		catch (IOException e)
		{
			System.out.println("Fuck you 'Image");
			e.printStackTrace();
		}

		return image;
	}

	public void setEnemyField(Field enemyField)
	{
		this.enemyField = enemyField;
	}
<<<<<<< HEAD
=======

>>>>>>> a05d704da415a51aff84a9dccb92fa25f1d1d3d3
}
