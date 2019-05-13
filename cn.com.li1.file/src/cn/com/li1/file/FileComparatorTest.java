package cn.com.li1.file;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class FileComparatorTest {

	@Test
	public void test() {

		List<File> list=null;
		Collections.sort(list, new Comparator<File>() {
            public int compare(File file, File newFile) {
                if (file.lastModified() < newFile.lastModified()) {
                    return -1;
                } else if (file.lastModified() == newFile.lastModified()) {
                    return 0;
                } else {
                    return 1;
                }

            }
        });
	}

}
