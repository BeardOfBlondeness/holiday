package boot;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Hook implements NativeKeyListener {
	
	private static Hook hook;
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		
		if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals("Close Bracket")) {
			GlobalScreen.removeNativeKeyListener(hook);
			try {
				GlobalScreen.unregisterNativeHook();
				//GlobalScreen.
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(1);;
		} else if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals("Quote")) {
			if(Overlay.edited) {
				Overlay.edited = false;
				StartUp.overlay.setMode();
			}else {
				Overlay.edited = true;
				StartUp.overlay.setMode();
			}
		}

	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
	}

	public static void Hooker() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			//System.err.println("There was a problem registering the native hook.");
			//System.err.println(ex.getMessage());

			System.exit(1);
		}
		hook = new Hook();
		GlobalScreen.addNativeKeyListener(hook);
	}
}