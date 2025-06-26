//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;
import java.util.Random;

import static java.awt.geom.Path2D.contains;

public class Main {


    public static boolean validateRawNumber(String rawNumber) {
        if (rawNumber == null || rawNumber.isEmpty()) {
            return false;
        }
        return rawNumber.matches("\\d{10}");
    }

    public static void testValidateRawNumber() {
        System.out.println(validateRawNumber("1234567890"));
        System.out.println(validateRawNumber("123"));
        System.out.println(validateRawNumber(""));
        System.out.println(validateRawNumber(null));
        System.out.println(validateRawNumber("1234567890987654321"));
        System.out.println(validateRawNumber("AAAAAAA"));
    }

    public static boolean validateFormatedNumber(String formattedNumber) {
        if (formattedNumber == null || formattedNumber.isEmpty()) {
            return false;
        }
        return formattedNumber.matches("\\+7\\d{6}-\\d{2}-\\d{2}");
    }

    public static void testValidateFormatedNumber() {
        System.out.println(validateFormatedNumber("+7800555-35-35"));
        System.out.println(validateFormatedNumber("+78005553535"));
        System.out.println(validateFormatedNumber(""));
        System.out.println(validateFormatedNumber("+1234567-33-33"));
    }

    public static String convertRawToFormatted(String rawNumber) {
        if (!validateRawNumber(rawNumber)) {
            return "error: invalid number format\n";
        }
        StringBuilder formattedNumber = new StringBuilder("+7" + rawNumber);
        formattedNumber.insert(8, '-');
        formattedNumber.insert(11, '-');
        return formattedNumber.toString();
    }

    public static void testConvertRawToFormatted () {
        String testNumber =  convertRawToFormatted("8005553535");
        if (validateFormatedNumber(testNumber)) {
            System.out.println(testNumber + " is valid");
        }
        else {
            System.out.println(testNumber + " is not valid");
        }
        testNumber =  convertRawToFormatted("");
        if (validateFormatedNumber(testNumber)) {
            System.out.println(testNumber + " is valid");
        }
        else {
            System.out.println(testNumber + " is not valid");
        }
        testNumber =  convertRawToFormatted("qwerty");
        if (validateFormatedNumber(testNumber)) {
            System.out.println(testNumber + " is valid");
        }
        else {
            System.out.println(testNumber + " is not valid");
        }
    }

    public static String convertFormattedToRaw(String formattedNumber) {
        if (!validateFormatedNumber(formattedNumber)) {
            return "error: invalid number format\n";
        }
        formattedNumber = formattedNumber.replaceAll("[-+]", "");
        return formattedNumber.substring(1);
    }

    public static void testFormattedToRaw() {
        System.out.println(convertFormattedToRaw("+7800555-35-35"));
        System.out.println(convertFormattedToRaw("+7111111-11-11"));
        System.out.println(convertFormattedToRaw("7800555-35-35"));
        System.out.println(convertFormattedToRaw(""));
        System.out.println(convertFormattedToRaw("+"));
    }

    public static String generateRawNumber(Random random) {
        StringBuilder rawNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            rawNumber.append(random.nextInt(10));
        }
        return rawNumber.toString();
    }

    public static void testGenerateRawNumber() {
        System.out.println(generateRawNumber(new Random()));
        System.out.println(generateRawNumber(new Random()));
        System.out.println(generateRawNumber(new Random()));
    }

    public static String generateFormattedNumber(Random random) {
        String formattedNum = generateRawNumber(random);
        return convertRawToFormatted(formattedNum);
    }

    public static void testGenerateFormattedNumber() {
        System.out.println(generateFormattedNumber(new Random()));
        System.out.println(generateFormattedNumber(new Random()));
        System.out.println(generateFormattedNumber(new Random()));
    }

    public static String[][] generateNumbers(int count, Random random) {
        String[][] numbers = new String[count][2];
        for (int i = 0; i < count; i++) {
            String newNum = generateRawNumber(random);
            numbers[i][0] = newNum;
            newNum = convertRawToFormatted(newNum);
            numbers[i][1] = newNum;
        }
        return numbers;
    }

    public static void printNumbers (String[][] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                System.out.print(numbers[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static boolean validateIPv4(String ipv4String) {
        if (ipv4String == null || ipv4String.isEmpty()) {
            return false;
        }
        return ipv4String.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}" +
                "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$");
    }

    public static void testValidateIPv4() {
        System.out.println(validateIPv4("255.255.255.255"));
        System.out.println(validateIPv4("0.0.555.255"));
        System.out.println(validateIPv4("180."));
        System.out.println(validateIPv4(""));
    }

    public static String generateRandomIPv4(Random random) {
        String[] octets = new String[4];
        for (int i = 0; i < 4; i++) {
            octets[i] = String.valueOf(random.nextInt(256));
        }
        return String.join(".", octets);
    }

    public static void testGenerateRandomIPv4(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(generateRandomIPv4(new Random()));
        }
    }

    public static String generateRandomText(int ipv4Count, double ipv4Percentage, Random random) {

        if (ipv4Count <= 0 || ipv4Percentage <= 0 || ipv4Percentage >= 1) {
            return "error: invalid input data";
        }
        // генерируем ip адреса и считаем их общую длину
        String[] ipv4s = new String[ipv4Count];
        int ipv4Length = 0;
        for (int i = 0; i < ipv4Count; i++) {
            ipv4s[i] = generateRandomIPv4(random);
            ipv4Length += ipv4s[i].length();
        }
        // общая длина текста
        int totalTextLength = (int) Math.round(ipv4Length / ipv4Percentage);
        int junkLength = totalTextLength - ipv4Length;
        StringBuilder junkText = generaeJunkText(junkLength, random);
        // позиции для вставки ip адресов
        int[] insertPositions = generateUniquePositions(ipv4Count, junkText.length(), random);
        Arrays.sort(insertPositions);

        for (int i = insertPositions.length - 1; i >= 0; i--) {
            junkText.insert(insertPositions[i], ipv4s[i] + " ");
        }
        return junkText.toString();
    }

    public static StringBuilder generaeJunkText(int length, Random random) {
        String numbers = "1234567890";
        String chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        String symbols = " \n\\\"!@#$%^&*()-_=+[{]}~,<.>/?|';:";
        String allChars = chars + symbols + numbers;

        StringBuilder junkText = new StringBuilder();
        for (int i = 0; i < length; i++) {
            junkText.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return junkText;
    }

    public static int[] generateUniquePositions(int count, int length, Random random) {
        int[] positions = new int[count];
        boolean isExist;
        for (int i = 0; i < count; i++) {
            int pos;
            isExist = false;
            do {
                pos = random.nextInt(length);
                for (int j = 0; j < i; j++) {
                    if (positions[j] == pos) {
                        isExist = true;
                        break;
                    }
                }
            } while (isExist);
            positions[i] = pos;
        }
        return positions;
    }

    public static String[] extractAllIPv4(String text) {
        int maxIPs = text.length() / 7;
        String[] tempIPs = new String[maxIPs];
        int ipCount = 0;

        int i = 0;
        int endLimit = text.length() - 6;

        while (i < endLimit) {
            char c = text.charAt(i);

            if (c >= '0' && c <= '9') {
                if (i == 0 || !(text.charAt(i - 1) >= '0' && text.charAt(i - 1) <= '9')) {
                    // Ищем пробел в пределах 15 символов вперёд
                    int spacePos = -1;
                    int searchLimit = Math.min(i + 15, text.length());

                    for (int j = i; j < searchLimit; j++) {
                        if (text.charAt(j) == ' ') {
                            spacePos = j;
                            break;
                        }
                    }

                    if (spacePos != -1) {
                        int length = spacePos - i;
                        if (length >= 7) {
                            String candidate = text.substring(i, spacePos);
                            if (validateIPv4(candidate)) {
                                tempIPs[ipCount++] = candidate;
                                i = spacePos + 1; // пропускаем всё, что было частью IP
                                continue; // не идём к i++, уже перескочили
                            }
                        }
                    }
                    i++; // только если не нашли валидный IP
                }
            } else {
                i++; // не цифра — двигаемся дальше
            }
        }

        String[] result = new String[ipCount];
        for (int k = 0; k < ipCount; k++) {
            result[k] = tempIPs[k];
        }

        return result;
    }

    public static void main(String[] args) {
        testValidateRawNumber();
        testValidateFormatedNumber();
        testConvertRawToFormatted();
        testFormattedToRaw();
        testGenerateRawNumber();
        testGenerateFormattedNumber();
        String[][] numbers = generateNumbers(10, new Random());
        printNumbers(numbers);
        testValidateIPv4();
        testGenerateRandomIPv4(5);

        String junkTet = generateRandomText(3, 0.5, new Random());
        System.out.println(junkTet);
        String[] ipv4s = extractAllIPv4(junkTet);
        System.out.println();
        for (String ipv4 : ipv4s) {
            System.out.println(ipv4);
        }
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

    }
}