package jobkorea;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		int PAGE;
		if (event.getSource() == nextbtn) {
			PAGE++;
		} else if (Event.getSource() == prevbtn) {
			PAGE--;
		} else { PAGE =1;
		}

	}

}
