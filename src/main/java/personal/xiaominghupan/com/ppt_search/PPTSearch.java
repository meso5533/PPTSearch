package personal.xiaominghupan.com.ppt_search;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

public class PPTSearch {
	public static void main(String[] args) {
		String path = args[0];
		String ptn = args[1];

		try {
			File file = new File(path);
			Pattern pattern = Pattern.compile(ptn);
			searchPPT(file, pattern);
		} catch (PatternSyntaxException e) {
			System.out.println("Invalid pattern : " + ptn);
		}
	}

	private static void searchPPT(File file, Pattern pattern) {
		String filename = file.getName();
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					searchPPT(f, pattern);
				}
			} else {
				if (file.getName().endsWith(".ppt") || file.getName().endsWith(".pptx")) {
					SlideShow ppt = null;
					try {
						ppt = new SlideShow(new HSLFSlideShow(file.getAbsolutePath()));
						Slide[] slides = ppt.getSlides();
						int slideCnt = slides.length;
						for (int i = 0; i < slideCnt; ++i) {
							for (TextRun run : slides[i].getTextRuns()) {
								String text = run.getText();
								Matcher matcher = pattern.matcher(text);
								if (matcher.find()) {
									System.out.println("\n"
											+ "[File : " + filename + ", "
											+ "Slide : " + (i + 1) + "]\n"
											+ text);
								}
							}
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						System.out.println("Something wrong when searching " + filename);
					} finally {
						// Is it neccessary to close ppt? And how?
						if (ppt != null) {
						}
					}
				}
			}
		} else {
			System.out.println("File or Dir not exists : " + filename);
		}
	}
}
