/*
 *  Copyright (C) 2010 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2010 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.androlib.decode;

import brut.androlib.Androlib;
import brut.androlib.ApkDecoder;
import brut.androlib.ApkOptions;
import brut.androlib.BaseTest;
import brut.androlib.TestUtils;
import brut.common.BrutException;
import brut.directory.ExtFile;
import brut.util.OS;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class VectorDrawableTest extends BaseTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        TestUtils.cleanFrameworkFile();
        sTmpDir = new ExtFile(OS.createTempDirectory());
        TestUtils.copyResourceDir(VectorDrawableTest.class, "decode/issue1456/", sTmpDir);
    }

    @AfterClass
    public static void afterClass() throws BrutException {
        OS.rmdir(sTmpDir);
    }

    @Test
    public void checkIfDrawableFileDecodesProperly() throws BrutException, IOException {
        String apk = "issue1456.apk";

        // decode issue1456.apk
        ApkDecoder apkDecoder = new ApkDecoder(new File(sTmpDir + File.separator + apk));
        sTestOrigDir = new ExtFile(sTmpDir + File.separator + apk + ".out");

        apkDecoder.setOutDir(new File(sTmpDir + File.separator + apk + ".out"));
        apkDecoder.decode();

        checkFileExists("res/drawable/ic_arrow_drop_down_black_24dp.xml");
        checkFileExists("res/drawable/ic_android_black_24dp.xml");
    }

    @Test
    public void decode() throws BrutException, IOException {

        String in = "D:\\develop\\project\\mine\\apk\\tool\\resources-debug.ap_";
//        String in = "D:\\develop\\project\\mine\\apk\\tool\\androidkugou-debug.apk";
        String out = in + "_out";
        ApkDecoder apkDecoder = new ApkDecoder(new File(in));
        sTestOrigDir = new ExtFile(out);

        apkDecoder.setOutDir(new File(out));
        apkDecoder.decode();
    }

    @Test
    public void build() throws BrutException, IOException {
//        String in = "D:\\develop\\project\\mine\\apk\\tool\\resources-debug.ap_";
        String in = "D:\\develop\\project\\mine\\apk\\tool\\androidkugou-debug.apk";
        String out = in + "_out";

        ApkOptions apkOptions = new ApkOptions();
//        apkOptions.useAapt2 = true;
        new Androlib(apkOptions).build(new File(out), new File(out + "\\test.apk"));
    }

    @Test
    public void buildR() throws BrutException, IOException {
        String in = "D:\\develop\\project\\mine\\apk\\tool\\androidkugou-debug.apk";
        String out = in + "_out";

        ApkOptions apkOptions = new ApkOptions();
        apkOptions.RPath = out;
        new Androlib(apkOptions).build(new File(out), new File(out + "\\test.apk"));
    }

    private void checkFileExists(String path) {
        File f = new File(sTestOrigDir, path);

        assertTrue(f.isFile());
    }
}
