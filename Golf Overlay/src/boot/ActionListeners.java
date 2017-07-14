package boot;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

public class ActionListeners implements MouseMotionListener {
	private JLabel usedLabel;
	
	public ActionListeners(JLabel usedLabel) {
		this.usedLabel = usedLabel;
	}
	
	public void mouseDragged(MouseEvent arg0) {
		if(StartUp.overlay.edited) {
			usedLabel.setLocation(arg0.getXOnScreen() - 2, arg0.getYOnScreen() - 2);
			System.out.println("dragged");
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
}
