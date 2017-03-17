package Helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

public class MyButton extends JButton {
	
	public MyButton(String text){
		super(text);
	}

	@Override
	protected void paintComponent(Graphics g) {
	    if (getModel().isArmed()) {
	      g.setColor(Color.lightGray);
	    } else {
	      g.setColor(getBackground());
	    }
	    g.fillOval(0, 0, getSize().width-1,getSize().height-1);

	    super.paintComponent(g);
	  }

	  protected void paintBorder(Graphics g) {
	    g.setColor(getForeground());
	    g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
	  }

	  Shape shape;
	  public boolean contains(int x, int y) {
	    if (shape == null || 
	      !shape.getBounds().equals(getBounds())) {
	      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
	    }
	    return shape.contains(x, y);
	  }
}
