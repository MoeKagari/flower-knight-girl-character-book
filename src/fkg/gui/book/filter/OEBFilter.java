package fkg.gui.book.filter;

import fkg.gui.book.data.CharacterData;

public class OEBFilter implements Filter {
	public static final StringInteger[] SIS = {//
			new StringInteger("原始", 1),//
			new StringInteger("进化", 2),//
			new StringInteger("无开花", 13),//
			new StringInteger("假开花", 4),//
			new StringInteger("真开花", 5),//
			new StringInteger("开花", 3),//
			new StringInteger("最高进化", 11),//
			new StringInteger("全部角色", 12),//
	};

	private final int oeb;

	public OEBFilter(int index) {
		this.oeb = SIS[index].getInteger();
	}

	@Override
	public boolean filter(CharacterData cd) {
		boolean flag = false;

		switch (this.oeb) {
			case 1:
			case 2:
			case 3:
				flag = cd.ci.getOeb() == this.oeb;
				break;
			case 4:
				flag = (cd.ci.getOeb() == 3) && cd.ci.getKariBloom();
				break;
			case 5:
				flag = (cd.ci.getOeb() == 3) && !cd.ci.getKariBloom();
				break;
		}
		switch (this.oeb) {
			case 11:
				switch (cd.ci.getOeb()) {
					case 1:
						flag = false;
						break;
					case 2:
						flag = !cd.ci.hasBloom();
						break;
					case 3:
						flag = true;
						break;
				}
				break;
			case 12:
				flag = true;
				break;
			case 13:
				switch (cd.ci.getOeb()) {
					case 1:
						flag = false;
						break;
					case 2:
						flag = !cd.ci.hasBloom();
						break;
					case 3:
						flag = false;
						break;
				}
				break;
		}

		return flag;
	}
}