package com.ouyexie.pg.utils;

import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author Ouye Xie
 */
public class ArrayUtil {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(StringUtil.class);

    public static boolean allZero(Double[] input) {
        return IntStream.range(0, input.length).parallel().allMatch(i -> input[i] == 0.0);
    }

    public static Double[][] merge(Double[][] first, Double[][] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        int r1 = first.length;
        int r2 = second.length;
        if (r1 != r2) {
            LOG.error("dimension mismatch for merging! return null");
            return null;
        }
        int c1 = first[0].length;
        int c2 = second[0].length;
        Double[][] result = new Double[r1][c1 + c2];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c1; j++) {
                result[i][j] = first[i][j];
            }
            for (int j = 0; j < c2; j++) {
                result[i][c1 + j] = second[i][j];
            }
        }
        return result;
    }

    public static <T> void outputMatrixToFile(T[][] matrix, String tdFileName, int numberOfRowsToRecord, boolean writeTitle) {

        Thread t = new Thread(new Runnable() {
            public void run() {
                int max = numberOfRowsToRecord;
                if (numberOfRowsToRecord < 0) {
                    max = matrix.length;
                }
                try {
                    FileWriter fw = new FileWriter("./output/" + tdFileName, false);
                    //title for arff file
                    int len = matrix[0].length;
                    if (writeTitle) {
                        String[] title = new String[len];
                        for (int i = 0; i < len; i++) {
                            title[i] = "term-" + i;
                        }
                        fw.write(String.format("%s\n", StringUtil.arrayToString(title)));
                    }
                    int index = 0;
                    for (T[] row : matrix) {
                        fw.write(String.format("%s\n", StringUtil.arrayToString(row)));
                        index++;
                        if (index >= max) {
                            break;
                        }
                    }
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LOG.info(String.format("file [%s] generated", tdFileName));
            }
        });
        t.start();
    }

    public static void outputMatrixToFile(double[][] matrix, String fileName, int numberOfRowsToRecord) {
        int I = matrix.length;
        int J = matrix[0].length;
        Double[][] m = new Double[I][J];
        for (int i = 0; i < I; i++) {
            for (int j = 0; j < J; j++) {
                m[i][j] = matrix[i][j];
            }
        }
        outputMatrixToFile(m, fileName, numberOfRowsToRecord, false);
    }

    public static <T> void outputMatrixDetailToFile(List<String> termList, List<String> codeList, Map<String, String[]> text, T[][] matrix, String tdFileName, int numberOfRowsToRecord) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                int max = numberOfRowsToRecord;
                if (numberOfRowsToRecord < 0) {
                    max = matrix.length;
                }
                try {
                    FileWriter fw = new FileWriter("./output/" + tdFileName, false);
                    fw.write(String.format("terms(%s): %s\n", termList.size(), StringUtil.listToString(termList)));
                    int index = 0;
                    for (String code : codeList) {
                        T[] elements = matrix[index];
                        fw.write(String.format("%s(%s)(%s): %s\n", code, StringUtil.arrayToString(text.get(code)), elements.length, StringUtil.arrayToString(elements)));
                        index++;
                        if (index >= max) {
                            break;
                        }
                    }
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LOG.info(String.format("file [%s] generated", tdFileName));
            }
        });
        t.start();
    }

    public static <T> List<T> findIntersection(T[] a, T[] b) {
        List<T> intersection = new LinkedList<>();
        for (T aa : a) {
            for (T bb : b) {
                if (Objects.equals(aa, bb)) {
                    intersection.add(aa);
                }
            }
        }
        return intersection;
    }

    public static <T> boolean match(T[] a, T[] b) {
        int aS = a.length;
        int bS = b.length;
        if (aS != bS) {
            return false;
        }
        boolean m = false;
        for (int i = 0; i < aS; i++) {
            if ((((Double) a[i]) > 0) && (((Double) b[i]) > 0)) {
                m = true;
                break;
            }
        }
        return m;
    }

    public static void main(String[] args) {
        String[] a = {"162703", "80000091", "161616", "240001", "76124972", "184693", "519021", "80009628", "500011", "76125030", "30007446", "340008", "钱广存", "76125033", "500015", "76125034", "沈玉云", "吴揆", "80553146", "70010604", "80062102", "002001", "80062101", "30049486", "30215487", "70010602", "吴建英", "贾红岩", "519018", "30248177", "30267626", "30267627", "沈芬", "80001597", "30267622", "30003502", "30267623", "257010", "30267624", "30267625", "80188285", "76125022", "76125023", "梁建业", "陈安琪", "500001", "30049111", "320001", "80030242", "217005", "30046744", "217001", "70010110", "80000154", "162201", "200008", "121099", "邹党生", "30402708", "002021", "70010109", "110010", "184719", "184718", "赵仙菊", "朱照荣", "王祥龙", "80064460", "70010107", "70010102", "76124946", "184689", "184722", "70010104", "70010103", "184720", "王若娟", "戚石萍", "630015", "80000048", "邓美琼", "121005", "周德芬", "121003", "310308", "73000065", "30012755", "002011", "184728", "30002160", "160603", "000011", "80002474", "001620", "30134623", "070006", "070001", "070002", "76124936", "30006952", "100016", "000780", "160605", "30013593", "30013594", "184700", "000001", "184703", "蒋大姐", "80002487", "163302", "070013", "71000033", "80000217", "30031115", "30398763", "邢约瑟", "30031114", "30031113", "30159368", "70022013", "80074107", "580001", "020005", "002031", "184708", "30013589", "213003", "80000073", "80000074", "80055664", "000831", "褚伟良", "80000075", "80041534", "80041533", "30006967", "30006969", "80059067", "30006968", "040007", "470006", "30056863", "30197195", "张正福", "30197198", "李红章", "80000080", "160219", "76124866", "80010169", "160611", "160610", "30006959", "80000123", "80000125", "30006955", "30006958", "10000018", "30006957", "80000007", "30006963", "30006962", "80044146", "202003", "30006965", "30006964", "10001075", "80044821", "30007015", "530003", "161706", "70010414", "30033517"};
        String[] b = {"30025708", "30025707", "李涛", "80010059", "80010054", "30025700", "500008", "80000012", "30025702", "30028779", "500006", "30025701", "30070871", "202009", "80000135", "30025704", "30025703", "30025706", "510050", "30025705", "500009", "500011", "魏丽华", "30240440", "80022047", "10000099", "000061", "161601", "80010062", "30240441", "30240442", "30393700", "80188285", "500001", "80571504", "77006933", "000051", "73001557", "110003", "80136276", "80000154", "80000035", "张武", "10001043", "10000077", "30047840", "510500", "30047841", "110010", "30023190", "70010106", "80064460", "70010101", "郭伟川", "184721", "70010103", "163801", "80066988", "162711", "510180", "10001395", "121002", "002011", "77001589", "任民", "150184", "罗锐", "80002356", "000021", "30030814", "赵正华", "000001", "80030499", "510880", "090001", "30029958", "163302", "30140360", "090003", "30140358", "10001130", "80014695", "80129712", "288001", "288002", "80000073", "80000074", "80000078", "519003", "30025692", "30025694", "30025693", "470007", "30025696", "30025695", "30025698", "30025697", "210001", "040002", "80057299", "80058823", "161026", "510300", "80000087", "30025699", "杨光", "10000018", "80006868", "30274333", "30029383", "80000091", "000696", "184698", "510330", "519029", "184690", "76125030", "80175022", "76125033", "76125034", "30027852", "30285078", "80553146", "30285077", "30269142", "80127622", "80010104", "30307072", "240010", "郑宏中", "163503", "楼文胜", "257010", "519011", "630001", "30058216", "莫建军", "76125025", "刘明亭", "80125677", "谢建强", "77002178", "320003", "沈冬梅", "76124954", "10000904", "76124959", "160706", "76124946", "184688", "100051", "050004", "李駸刚", "黄金森", "630016", "050001", "050002", "80015480", "吴兴旺", "510130", "001620", "80002872", "71000027", "070006", "070001", "202202", "黄忠", "80000206", "30049964", "楼晓楼", "30016223", "560003", "80037720", "80006209", "070013", "80074104", "邓春华", "30330706", "吴懿兵", "丘雪梅", "80203835", "73001916", "159919", "610001", "刘峰", "林香英", "80067370", "30215585", "10001081", "80044819", "10000418", "80010048", "叶美清", "519994", "80000123", "80000007", "202002", "郑鸿福", "77001549", "202001", "660006", "10001075", "73012913", "30277687", "30361522", "30361523", "73048409", "80003190", "30215576"};
        String[] c = {"162703", "80010297", "519300", "500006", "202005", "30352820", "30025399", "80079728", "80079727", "80079726", "陈玉婵", "庞建", "80079725", "73003089", "80065559", "杨莉芳", "80065558", "80065557", "30001963", "卢瑞洪", "80001233", "80188285", "30047291", "黄健", "30047290", "80065562", "80065561", "000051", "80065560", "290002", "80058458", "519688", "70010110", "30059385", "483003", "何峻", "110009", "460002", "30051771", "80000704", "30315858", "80064225", "70010106", "80064460", "80063130", "70010107", "180002", "70010102", "70010101", "163803", "163801", "刘叶平", "王雷雷", "30032105", "30031499", "30032104", "002011", "80045153", "73003058", "30043699", "80100857", "江月", "30140909", "30140908", "30140907", "曾雨薇", "80058394", "30203854", "30203853", "30182665", "30036919", "30036918", "80079881", "80079880", "80000181", "000001", "80002366", "80079767", "090006", "80000501", "周中东", "80053708", "80079879", "100026", "288002", "160505", "80000075", "80079878", "80002376", "80079877", "80079876", "80079875", "80079874", "80079873", "何林璇", "30043560", "80031489", "80044541", "360001", "80016893", "530005", "80016894", "30068030", "80016898", "184698", "510330", "240001", "519029", "30025425", "30025427", "30025426", "400001", "80056451", "184695", "80056450", "481004", "30025430", "76125033", "30025434", "30050862", "80074799", "30050865", "80553146", "30050864", "30050867", "30050866", "80014702", "80055257", "001772", "519018", "519013", "30025437", "80044119", "30025439", "257010", "519011", "30025441", "30026530", "30025443", "80102691", "80321661", "刘庭兰", "80117110", "30025407", "80000555", "80001645", "周滇", "80043003", "30052903", "80082097", "30052904", "160706", "050004", "050009", "30041688", "050008", "050001", "050002", "30025421", "姜传平", "80014274", "150103", "80055228", "30009197", "76124896", "001620", "30058674", "80000574", "30001411", "吴强", "070003", "30031501", "30031500", "30211080", "519180", "80021277", "80017457", "陈先群", "30196665", "160311", "270005", "30167660", "80003855", "80000468", "30165238", "30165239", "30165234", "姜艳明", "沈国英", "孙永建", "070021", "80003067", "10000786", "159919", "80000228", "30162553", "80074337", "80002090", "519994", "80000123", "202001", "张桂全", "30061247", "80032843", "30162548"};
        List<String> intersection = findIntersection(b, c);
        System.out.println(intersection);
    }
}
