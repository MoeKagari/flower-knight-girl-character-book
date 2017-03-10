package show;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import show.config.ShowConfig;
import tool.IndexFinder;

/**
 * 右侧显示skill
 */
@SuppressWarnings("serial")
public class SkillPanel extends JPanel {
	private final CharacterData[] cds = new CharacterData[3];
	private final JRadioButton[] jrb_oeb = new JRadioButton[3];
	private final String[] string_oeb = { "原始", "进化", "开花" };
	private BufferedImage image;
	private ShowPanel sp;
	private JButton lihui, valentine;
	private ShowOther showStand, showValentine;

	public SkillPanel() {
		this.sp = new ShowPanel();
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(451, 707));
		/*-----------------------------------------------------------*/
		JPanel north = new JPanel();
		ButtonGroup bg = new ButtonGroup();

		for (int i = 0; i < this.jrb_oeb.length; i++) {
			this.jrb_oeb[i] = new JRadioButton(this.string_oeb[i]);
			this.jrb_oeb[i].setFocusPainted(false);
			this.jrb_oeb[i].setEnabled(false);
			int mode = i;
			this.jrb_oeb[i].addActionListener(ev -> this.reflash(mode + 1));
			bg.add(this.jrb_oeb[i]);
			north.add(this.jrb_oeb[i]);
		}

		this.showStand = new ShowOther(ShowConfig.CHARACTER_STAND, 960, 640);
		this.lihui = new JButton("立绘");
		this.lihui.setFocusPainted(false);
		this.lihui.addActionListener(ev -> this.showOther(this.showStand, CharacterData::getStand));
		north.add(this.lihui);

		this.showValentine = new ShowOther(ShowConfig.CHARACTER_VALENTINE, 960, 640);
		this.valentine = new JButton("情人节");
		this.valentine.setFocusPainted(false);
		this.valentine.setToolTipText("2017情人节");
		this.valentine.addActionListener(ev -> this.showOther(this.showValentine, CharacterData::getValentine));
		north.add(this.valentine);

		this.add(north, BorderLayout.NORTH);
		this.add(this.sp, BorderLayout.CENTER);
	}

	private void showOther(ShowOther so, Function<CharacterData, BufferedImage> fun) {
		int index = IndexFinder.find(this.jrb_oeb, jrb -> jrb.isSelected());
		if (index != -1) {
			CharacterData cd = this.cds[index];
			if (cd != null) {
				so.display(cd.getId(), fun.apply(cd));
			}
		}
	}

	private void reflash(int mode) {
		this.jrb_oeb[mode - 1].setSelected(true);
		this.valentine.setEnabled(this.cds[mode - 1].getValentine() != null);
		this.image = this.cds[mode - 1].getSkillImage();
		this.sp.repaint();
	}

	public void dispose() {
		this.showStand.getWindow().dispose();
		this.showValentine.getWindow().dispose();
	}

	public void showCharacter(CharacterData cd) {
		if (cd == null) return;

		final int oeb = cd.getOEB();
		int id = cd.getId() - (oeb == 2 ? 1 : (oeb == 3 ? 300000 : 0));
		this.cds[0] = CharacterData.get().get(id);
		this.cds[1] = CharacterData.get().get(id + 1);
		this.cds[2] = CharacterData.get().get(id + 300000);

		for (int i = 0; i < this.cds.length; i++) {
			this.jrb_oeb[i].setEnabled(this.cds[i] != null);
			this.jrb_oeb[i].setSelected(false);
		}

		this.reflash(oeb);
	}

	private class ShowPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (SkillPanel.this.image != null) {
				g.drawImage(SkillPanel.this.image, 0, 0, this);
			}
		}
	}

}
