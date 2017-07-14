package boot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Overlay {

	private File[] images;
	private JFrame frame;
	private JLabel marker;
	private JLabel background;
	private BufferedImage icon;
	private ImageIcon renImg;
	private int index;
	private Dimension screenSize;
	private int width;
	private int height;
	private PointerInfo a;
	private Point b;
	private int x;
	private int y;
	public static boolean edited = false;
	private JLabel mode;
	private JLabel play;
	private ArrayList<JLabel> marks;
	private Color color;
	private JLabel plus;
	private int i = 1;

	private int imageIndex;
	public Overlay() {
		imageIndex = 1;
		marks = new ArrayList<JLabel>();
		color = new Color(200, 50, 0, 200);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)screenSize.getHeight();
		width = (int)screenSize.getWidth();

		images = new File[3];
		try {
			images[0] = new File("res/Line.bmp");
			images[1] = new File("res/dot.png");
			images[2] = new File("res/cross.png");
			icon = ImageIO.read(images[index]);
			renImg = new ImageIcon(icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index = 0;
		frame = new JFrame("Transparent Window");
		frame.setUndecorated(true);
		background = new JLabel();
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setAlwaysOnTop(true);
		frame.setSize(1920, 1080);

		// Without this, the window is draggable from any non transparent
		// point, including points  inside textboxes.
		frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
		frame.getContentPane().setLayout(null);

		background.setBounds(0, 0, width, height);

		JLabel inst = new JLabel(" Press # to switch between edit and play mode");
		inst.setFont(new Font("Arial", Font.BOLD, 20));
		inst.setOpaque(true);
		inst.setBackground(new Color(200, 200, 200, 100));
		inst.setForeground(Color.black);
		inst.setBounds((width/2)-225, height-100, 450, 45);

		mode = new JLabel("Play Mode");
		mode.setFont(new Font("Arial", Font.BOLD, 20));
		mode.setOpaque(true);
		mode.setBackground(new Color(200, 200, 200, 100));
		mode.setForeground(Color.black);
		mode.setBounds((width)-120, height/2, 100, 45);

		play = new JLabel("Edit Mode");
		play.setFont(new Font("Arial", Font.BOLD, 20));
		play.setOpaque(true);
		play.setBackground(new Color(200, 200, 200, 100));
		play.setForeground(Color.black);
		play.setBounds((width)-120, height/2, 100, 45);

		plus = new JLabel(" +");
		plus.setFont(new Font("Arial", Font.BOLD, 20));
		plus.setOpaque(true);
		plus.setBackground(new Color(0, 200, 0, 100));
		plus.setForeground(Color.black);
		plus.setBounds((width)-50, height/2 - 300, 25, 25);

		plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				marks.get(imageIndex).setVisible(true);
				imageIndex++;
			}
		});

		frame.add(plus);
		frame.add(play);
		play.setVisible(false);
		frame.add(mode);
		frame.add(inst);
		NoEdit();
		ColourChange();
		for(int q = 1; q < 18; q++) {
			JLabel newLabel = new JLabel();
			newLabel.setBackground(color);
			newLabel.setOpaque(true);
			newLabel.setBounds(0, 0, 5, 30);

			marks.add(newLabel);
			frame.add(marks.get(i));
			i++;
			newLabel.setVisible(false);

			marks.get(q).addMouseMotionListener(new ActionListeners(marks.get(q)));
		}

		frame.getContentPane().add(background);
		Edit();

		frame.setVisible(true);
		Hook.Hooker();
	}


	public void NoEdit() {

		marker = new JLabel();
		marker.setBackground(color);
		marker.setOpaque(true);
		marker.setBounds(400,900, 5, 30);
		frame.add(marker);
		marks.add(marker);
	}


	public void Edit() {
		marker.addMouseMotionListener(new MouseMotionAdapter() {
			//override the method
			public void mouseDragged(MouseEvent arg0) {
				if(edited) {
					a = MouseInfo.getPointerInfo();
					b = a.getLocation();
					x = (int) b.getX();
					y = (int) b.getY();
					marker.setLocation(x-2,y-2);
					System.out.println("dragged");
				}
			}
		});
	} 
	private JColorChooser colorPick;
	
	public void ColourChange() {
		JLabel colour = new JLabel("Colour");
		colour.setFont(new Font("Arial", Font.BOLD, 20));
		colour.setOpaque(true);
		colour.setBackground(new Color(0, 200, 0, 100));
		colour.setForeground(Color.black);
		colour.setBounds((width)-100, height/2 - 400, 70, 45);
		 //HsvChooserPanel2 hsvChooserPanel = new HsvChooserPanel2(COLOR_PROP)
		colorPick = new JColorChooser(new ColorSelectionModel());
		colorPick.setBounds(300, 300, 400, 400);
		frame.add(colorPick);
		
		colour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Make Colour Pick
			}
		});
		
		frame.add(colour);
	}

	public void setMode() {
		if(edited) {
			mode.setVisible(false);
			play.setVisible(true);
		}else {
			play.setVisible(false);
			mode.setVisible(true);
		}

	}
}
