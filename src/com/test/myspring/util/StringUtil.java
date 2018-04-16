package com.test.myspring.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �ַ�����֤������
 */
public class StringUtil {
	
	/**
	 * �������ַ���������ĸ�ĳɴ�д
	 * 
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
	
	/**
	 * �ж��û������ʱ���ʽ�Ƿ���ȷ
	 */
	public static boolean checkDateTime(String inputDate) {
		String DATE_TIME_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DATE_TIME_FORMAT, Locale.CHINA);
		simpleDateFormat.setLenient(false);
		boolean check = false;
		try {
			simpleDateFormat.parse(inputDate);
			check = true;
		} catch (Exception ex) {
			check = false;
			ex.printStackTrace();
		}

		return check;
	}

	/**
	 * �˷����ж������ַ��Ƿ�Ϊ����0-9 �Ƿ���true���Ƿ���false
	 * 
	 * @param c
	 *            char
	 * @return boolean
	 */
	public static boolean isDigit(char c) {
		return (('0' <= c) && (c <= '9'));
	}

	public static boolean isDigit(String inputStr) {
		char tempChar;
		for (int i = 0; i < inputStr.length(); i++) {
			tempChar = inputStr.charAt(i);
			// ����ַ�����һ���ַ����������򷵻�false
			if (!isDigit(tempChar)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �˷����ж������ַ��Ƿ�Ϊ��ĸa-z��A-Z �Ƿ���true���Ƿ���false
	 * 
	 * @param c
	 *            char
	 * @return boolean
	 */
	public static boolean isAlpha(char c) {
		return ((('a' <= c) && (c <= 'z')) || (('A' <= c) && (c <= 'Z')));
	}

	public static boolean isAlpha(String inputStr) {
		char tempChar;
		for (int i = 0; i < inputStr.length(); i++) {
			tempChar = inputStr.charAt(i);
			if (!isAlpha(tempChar)) { // ����ַ�����һ���ַ�������ĸ�򷵻�false
				return false;
			}
		}

		return true;
	}

	/**
	 * �˷������ڼ��������û����Ƿ�Ϸ����û�������ֻ��ʹ��Ӣ����ĸ�������Լ�-��_���������ַ�����Ϊ��ĸ������ �������ַ�����Ϊ��ĸ������
	 * 
	 * @param inputStr
	 *            ����
	 * @return boolean
	 */
	public static boolean checkUserNamePassword(String inputStr) {
		for (int nIndex = 0; nIndex < inputStr.length(); nIndex++) {
			char cCheck = inputStr.charAt(nIndex);
			if (nIndex == 0 && (cCheck == '-' || cCheck == '_')) {
				return false;
			}
			if (!(isDigit(cCheck) || isAlpha(cCheck) || cCheck == '-' || cCheck == '_')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �˷������email��Ч�� ������ʾ��Ϣ
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		// �����ʼ�
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();

		return isMatched;
	}

	/**
	 * �˷��������������֤����Ч�� ������ʾ��Ϣ ������ز���������ͨ����֤
	 * 
	 * @param IDNumber
	 *            ���֤��
	 * @return String
	 */
	public static boolean checkIDNumber(String IDNumber) {
		boolean result = IDNumber.matches("[0-9]{15}|[0-9]{17}[0-9X]");
		if (result) {
			int year, month, date;
			if (IDNumber.length() == 15) {
				year = Integer.parseInt(IDNumber.substring(6, 8));
				month = Integer.parseInt(IDNumber.substring(8, 10));
				date = Integer.parseInt(IDNumber.substring(10, 12));
			} else {
				year = Integer.parseInt(IDNumber.substring(6, 10));
				month = Integer.parseInt(IDNumber.substring(10, 12));
				date = Integer.parseInt(IDNumber.substring(12, 14));
			}
			switch (month) {
			case 2:
				result = (date >= 1)
						&& (year % 4 == 0 ? date <= 29 : date <= 28);
				break;
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				result = (date >= 1) && (date <= 31);
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				result = (date >= 1) && (date <= 31);
				break;
			default:
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * 1������Ľṹ ������ݺ�������������룬��ʮ��λ���ֱ������һλУ������ɡ�����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬
	 * ��λ����˳�����һλ����У���롣
	 * 
	 * 2����ַ��(ǰ��λ���� ��ʾ�������ס����������(�С��졢��)�������������룬��GB/T2260�Ĺ涨ִ�С�
	 * 
	 * 3�����������루����λ��ʮ��λ�� ��ʾ�������������ꡢ�¡��գ���GB/T7408�Ĺ涨ִ�У��ꡢ�¡��մ���֮�䲻�÷ָ�����
	 * 
	 * 4��˳���루��ʮ��λ��ʮ��λ��
	 * ��ʾ��ͬһ��ַ������ʶ������Χ�ڣ���ͬ�ꡢͬ�¡�ͬ�ճ������˱ඨ��˳��ţ�˳�����������������ԣ�ż�������Ů�ԡ�
	 * 
	 * 5��У���루��ʮ��λ���� ��1��ʮ��λ���ֱ������Ȩ��͹�ʽ S = Sum(Ai * Wi), i = 0, , 16
	 * ���ȶ�ǰ17λ���ֵ�Ȩ��� Ai:��ʾ��iλ���ϵ����֤��������ֵ Wi:��ʾ��iλ���ϵļ�Ȩ���� Wi: 7 9 10 5 8 4 2 1 6
	 * 3 7 9 10 5 8 4 2 ��2������ģ Y = mod(S, 11) ��3��ͨ��ģ�õ���Ӧ��У���� Y: 0 1 2 3 4 5 6 7
	 * 8 9 10 У����: 1 0 X 9 8 7 6 5 4 3 2
	 * 
	 * */

	/**
	 * �ж��ֻ������Ƿ�Ϸ�
	 * 
	 * @param handset
	 *            �ֻ���
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean isHandset(String handset) {
		try {
			if (!handset.substring(0, 1).equals("1")) {
				return false;
			}
			if (handset == null || handset.length() != 11) {
				return false;
			}
			// String check = "^[0123456789]+$";
			String check = "^1[3-8]\\d{9}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(handset);

			return matcher.matches();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * �жϴ�½�����̻���С��ͨ ���ţ�010,020,021,022,023,024,025,027,028,029
	 * 
	 * @param tel
	 *            �绰����
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean isTel(String tel) {
		try {
			String check = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(tel);

			return matcher.matches();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��֤�Ƿ��������
	 * 
	 * @param str
	 * @return �Ƿ��������:��������-true��û������-false
	 */
	public static boolean containChinese(String str) {
		// String check = "^[\u4e00-\u9fa5]{1,}$";

		String check = "^[\\u4e00-\\u9fa5]+?";

		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(str);

		return matcher.find();
	}

	/**
	 * ��֤�Ƿ�����ո�
	 * 
	 * @param str
	 * @return �Ƿ�����ո�
	 */
	public static boolean containBlank(String str) {
		if (str.length() > str.replace(" ", "").length()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��֤�û����Ƿ�ֻ����Ӣ�ĺ�����
	 * 
	 * @param userName
	 *            �û���
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean isUserName(String userName) {
		String check = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(userName);

		return matcher.matches();
	}

	/**
	 * ���֤����Ч��֤
	 * 
	 * @param IDStr
	 *            ���֤��
	 * @return ��Ч��true ��Ч��false
	 * @throws ParseException
	 */
	public boolean IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// ��¼������Ϣ
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		// String[] Checker = {"1","9","8","7","6","5","4","3","2","1","1"};
		String Ai = "";

		// ================ ����ĳ��� 15λ��18λ ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "���볤��Ӧ��Ϊ15λ��18λ��";
			System.out.println(errorInfo);
			return false;
		}
		// =======================(end)========================

		// ================ ���� �������Ϊ��Ϊ���� ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "15λ���붼ӦΪ���� ; 18λ��������һλ�⣬��ӦΪ���֡�";
			System.out.println(errorInfo);
			return false;
		}
		// =======================(end)========================

		// ================ ���������Ƿ���Ч ================
		String strYear = Ai.substring(6, 10);// ���
		String strMonth = Ai.substring(10, 12);// �·�
		String strDay = Ai.substring(12, 14);// �·�

		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "������Ч��";
			System.out.println(errorInfo);
			return false;
		}

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(
						strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "���ղ�����Ч��Χ��";
			System.out.println(errorInfo);
			return false;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "�·���Ч";
			System.out.println(errorInfo);
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "������Ч";
			System.out.println(errorInfo);
			return false;
		}
		// =====================(end)=====================

		// ================ ������ʱ����Ч ================
		Hashtable<String, String> h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "�����������";
			System.out.println(errorInfo);
			return false;
		}
		// ==============================================

		// ================ �ж����һλ��ֵ ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "���֤��Ч�����һλ��ĸ����";
				System.out.println(errorInfo);
				return false;
			}
		} else {
			System.out.println("���ڵ���:" + h.get(Ai.substring(0, 2).toString()));
			System.out.println("�����֤��:" + Ai);
			return true;
		}
		// =====================(end)=====================
		System.out.println("���ڵ���:" + h.get(Ai.substring(0, 2).toString()));
		return true;
	}

	/**
	 * ======================================================================
	 * ���ܣ����õ�������
	 * 
	 * @return Hashtable ����
	 */
	public Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "����");
		hashtable.put("12", "���");
		hashtable.put("13", "�ӱ�");
		hashtable.put("14", "ɽ��");
		hashtable.put("15", "���ɹ�");
		hashtable.put("21", "����");
		hashtable.put("22", "����");
		hashtable.put("23", "������");
		hashtable.put("31", "�Ϻ�");
		hashtable.put("32", "����");
		hashtable.put("33", "�㽭");
		hashtable.put("34", "����");
		hashtable.put("35", "����");
		hashtable.put("36", "����");
		hashtable.put("37", "ɽ��");
		hashtable.put("41", "����");
		hashtable.put("42", "����");
		hashtable.put("43", "����");
		hashtable.put("44", "�㶫");
		hashtable.put("45", "����");
		hashtable.put("46", "����");
		hashtable.put("50", "����");
		hashtable.put("51", "�Ĵ�");
		hashtable.put("52", "����");
		hashtable.put("53", "����");
		hashtable.put("54", "����");
		hashtable.put("61", "����");
		hashtable.put("62", "����");
		hashtable.put("63", "�ຣ");
		hashtable.put("64", "����");
		hashtable.put("65", "�½�");
		hashtable.put("71", "̨��");
		hashtable.put("81", "���");
		hashtable.put("82", "����");
		hashtable.put("91", "����");
		return hashtable;
	}

	/**
	 * ======================================================================
	 * ���ܣ��ж��ַ����Ƿ�Ϊ����
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric1(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ���ܣ��ж��ַ����Ƿ�Ϊ���ڸ�ʽ
	 * 
	 * @param strDate
	 *            �ַ���
	 * @return
	 */
	public boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * ======================================================================
	 * =����:���ж��Ѿ�����ȷ�����֤����֮��,���ҳ����֤���ڵ���
	 * 
	 * @param idCard
	 *            ���֤����
	 * @return ���ڵ���
	 */
	public String GetArea(String idCard) {
		Hashtable<String, String> ht = GetAreaCode();
		String area = ht.get(idCard.substring(0, 2));
		return area;
	}

	/**
	 * ======================================================================
	 * =����:���ж��Ѿ�����ȷ�����֤����֮��,���ҳ������Ա�
	 * 
	 * @param idCard
	 *            ���֤����
	 * @return �л���Ů
	 */
	public String GetSex(String idCard) {
		String sex = "";
		if (idCard.length() == 15)
			sex = idCard.substring(idCard.length() - 3, idCard.length());

		if (idCard.length() == 18)
			sex = idCard.substring(idCard.length() - 4, idCard.length() - 1);

		System.out.println(sex);
		int sexNum = Integer.parseInt(sex) % 2;
		if (sexNum == 0) {
			return "Ů";
		}
		return "��";
	}

	/**
	 * ======================================================================
	 * =����:���ж��Ѿ�����ȷ�����֤����֮��,���ҳ����˳�������
	 * 
	 * @param idCard
	 *            ���֤����
	 * @return �������� XXXX MM-DD
	 */

	public String GetBirthday(String idCard) {
		String Ain = "";
		if (idCard.length() == 18) {
			Ain = idCard.substring(0, 17);
		} else if (idCard.length() == 15) {
			Ain = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
		}

		// ================ ���������Ƿ���Ч ================
		String strYear = Ain.substring(6, 10);// ���
		String strMonth = Ain.substring(10, 12);// �·�
		String strDay = Ain.substring(12, 14);// ����
		return strYear + "-" + strMonth + "-" + strDay;
	}

	/**
	 * �Ƿ�������
	 * 
	 * @param chineseStr
	 * @return
	 */
	public boolean existsChinese(String chineseStr) {
		char[] charArray = chineseStr.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���ֽ���дת����˼����д��������Ȼ������ʰ�滻���� Ҫ�õ�������ʽ
	 */
	public static String digitUppercase(double n) {
		String fraction[] = { "��", "��" };
		String digit[] = { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };
		String unit[][] = { { "Ԫ", "��", "��" }, { "", "ʰ", "��", "Ǫ" } };

		String head = n < 0 ? "��" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
					.replaceAll("(��.)+", "");
		}
		if (s.length() < 1) {
			s = "��";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(��.)*��$", "").replaceAll("^$", "��") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(��.)*��Ԫ", "Ԫ").replaceFirst("(��.)+", "")
						.replaceAll("(��.)+", "��").replaceAll("^��$", "��Ԫ��");
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * ���� �ָ��� ���ַ��� ��ֳ�String����
	 * @param str
	 * @param splitsign
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null) {
			return null;
		}
		ArrayList al = new ArrayList();
		while ((index = str.indexOf(splitsign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		al.add(str);
		return (String[]) al.toArray(new String[0]);
	}

	/**
	 * ����ָ����ʽ�滻�ַ���
	 * @param from
	 * @param to
	 * @param source
	 * @return
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer str = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			str.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		str.append(source);
		return str.toString();
	}

	/**
	 * /**
	 * HTML�����ʽ
	 * @param str
	 * @return
	 */
	public static String htmlencode(String str) {
		if (str == null) {
			return null;
		}
		return replace("'", "'", replace("<", "<", str));
	}

	/**
	 * HTML�����ʽ
	 * @param str
	 * @return
	 */
	public static String htmldecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("'", "'", replace("<", "<", str));
	}

	private static final String _BR = "";

	/**
	 * תHTML��ʽ
	 * @param str
	 * @return
	 */
	public static String htmlshow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "<", str);
		str = replace(" ", " ", str);
		str = replace("\r\n", _BR, str);
		str = replace("\n", _BR, str);
		str = replace("\t", "    ", str);
		return str;
	}

	/**
	 * ��ȡָ�����ȵ��ַ��� ...
	 * @param str
	 * @param length
	 * @return
	 */
	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception e) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	/**
	 * �Ƿ���Integer
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * �Ƿ���Double
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * �Ƿ����ż�
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * ��֤�ַ���
	 * @param content
	 * @return
	 */
	public static String parse(String content) {
		String email = null;
		if (content == null || content.length() < 1) {
			return email;
		}
		// �ҳ�����@
		int beginPos;
		int i;
		String token = "@";
		String preHalf = "";
		String sufHalf = "";

		beginPos = content.indexOf(token);
		if (beginPos > -1) {
			// ǰ��ɨ��
			String s = null;
			i = beginPos;
			while (i > 0) {
				s = content.substring(i - 1, i);
				if (isLetter(s))
					preHalf = s + preHalf;
				else
					break;
				i--;
			}
			// ����ɨ��
			i = beginPos + 1;
			while (i < content.length()) {
				s = content.substring(i, i + 1);
				if (isLetter(s))
					sufHalf = sufHalf + s;
				else
					break;
				i++;
			}
			// �жϺϷ���
			email = preHalf + "@" + sufHalf;
			if (isEmail(email)) {
				return email;
			}
		}
		return null;
	}

	/**
	 * �Ƿ���Email��ʽ���ַ���
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}

	/**
	 * �Ƿ�������
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * �Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * �Ƿ�������
	 * @param x
	 * @return
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}

	/**
	 * ת��д���
	 * @param str
	 * @return
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { 'ʰ', '��', 'Ǫ' }; // ����λ�ñ�ʾ
		char[] vunit = { '��', '��' }; // ������ʾ
		char[] digit = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��' }; // ���ֱ�ʾ
		long midVal = (long) (value * 100); // ת��������
		String valStr = String.valueOf(midVal); // ת�����ַ���

		String head = valStr.substring(0, valStr.length() - 2); // ȡ��������
		String rail = valStr.substring(valStr.length() - 2); // ȡС������

		String prefix = ""; // ��������ת���Ľ��
		String suffix = ""; // С������ת���Ľ��
		// ����С����������
		if (rail.equals("00")) { // ���С������Ϊ0
			suffix = "��";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "��"
					+ digit[rail.charAt(1) - '0'] + "��"; // ����ѽǷ�ת������
		}
		// ����С����ǰ�����
		char[] chDig = head.toCharArray(); // ����������ת�����ַ�����
		char zero = '0'; // ��־'0'��ʾ���ֹ�0
		byte zeroSerNum = 0; // ��������0�Ĵ���
		for (int i = 0; i < chDig.length; i++) { // ѭ������ÿ������
			int idx = (chDig.length - i - 1) % 4; // ȡ����λ��
			int vidx = (chDig.length - i - 1) / 4; // ȡ��λ��
			if (chDig[i] == '0') { // �����ǰ�ַ���0
				zeroSerNum++; // ����0��������
				if (zero == '0') { // ��־
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // ����0��������
			if (zero != '0') { // �����־��Ϊ0,�����,������,��ʲô��
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // ת�������ֱ�ʾ
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // �ν���λ��Ӧ�ü��϶�������,��
			}
		}

		if (prefix.length() > 0)
			prefix += 'Բ'; // ����������ִ���,����Բ������
		return prefix + suffix; // ������ȷ��ʾ
	}

	/**
	 * ɾ���ظ��ַ�
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String removeSameString(String str) {
		Set mLinkedSet = new LinkedHashSet();// set���ϵ����������Ӽ��������ظ�
		String[] strArray = str.split(" ");// ���ݿո�(������ʽ)�ָ��ַ���
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		System.out.println(mLinkedSet);
		return sb.toString();
	}

	/**
	 * ����
	 * @param src
	 * @return
	 */
	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null) {
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++) {
				switch (src.charAt(pos)) {
				case '"':
					result.append("'");
					break;
				case '<':
					result.append("<");
					break;
				case '>':
					result.append(">");
					break;
				case '\'':
					result.append("'");
					break;
				case '&':
					result.append("&");
					break;
				case '%':
					result.append("&pc;");
					break;
				case '_':
					result.append("&ul;");
					break;
				case '#':
					result.append("&shap;");
					break;
				case '?':
					result.append("&ques;");
					break;
				default:
					result.append(src.charAt(pos));
					break;
				}
			}
		}
		return result.toString();
	}

	/**
	 * ����
	 * @param src
	 * @return
	 */
	public static String decoding(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("'", "'").replace("'", "\'");
		result = result.replace("<", "<").replace(">", ">");
		result = result.replace("&", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}

	// ------------------------------------------------------------------------------------------

	/**
	 * ���ַ���ת��Ϊ int.
	 * 
	 * @param input
	 *            ������ִ�
	 * @date 2005-07-29
	 * @return �������
	 */
	public static int parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * ��ʽ�����ڵ���ʱ����ʱ���ʽ����ʾ. d�� HH:mm:ss
	 * 
	 * @return - String ��ʽ�����ʱ��
	 */
	public static String formatDateToDHMSString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"d�� HH:mm:ss");

		return dateformat.format(date);

	}

	/**
	 * ��ʽ�����ڵ�ʱ����ʱ���ʽ����ʾ.
	 * 
	 * @return - String ��ʽ�����ʱ��
	 */
	public static String formatDateToHMSString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"HH:mm:ss");

		return dateformat.format(date);

	}

	/**
	 * ��ʱ����ʱ���ʽ���ַ���ת��Ϊ����.
	 * 
	 * @param input
	 * @return
	 */
	public static Date parseHMSStringToDate(String input) {
		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"HH:mm:ss");

		try {
			return dateformat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ��ʽ�����ڵ� Mysql ���ݿ����ڸ�ʽ�ַ�������ʾ.
	 * 
	 * @return - String ��ʽ�����ʱ��
	 */
	public static String formatDateToMysqlString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		return dateformat.format(date);

	}

	/**
	 * �� Mysql ���ݿ����ڸ�ʽ�ַ���ת��Ϊ����.
	 * 
	 * @param input
	 * @return
	 */
	public static Date parseStringToMysqlDate(String input) {
		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			return dateformat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ����ʱ���ַ���, �ɶ���ʽ��, M��d�� HH:mm ��ʽ. 2004-09-22, LiuChangjiong
	 * 
	 * @return - String ��ʽ�����ʱ��
	 */
	public static String formatDateToMMddHHmm(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"M��d�� HH:mm");

		return dateformat.format(date);
	}

	/**
	 * ����ʱ���ַ���, �ɶ���ʽ��, yy��M��d��HH:mm ��ʽ. 2004-10-04, LiuChangjiong
	 * 
	 * @return - String ��ʽ�����ʱ��
	 */
	public static String formatDateToyyMMddHHmm(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"yy��M��d��HH:mm");

		return dateformat.format(date);
	}

	/**
	 * ����һ�� 18 λ�� yyyyMMddHHmmss.SSS ��ʽ�������ַ���.
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String genTimeStampString(Date date) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss.SSS");
		return df.format(date);
	}

	/**
	 * ���ַ��� source �е� oldStr �滻Ϊ newStr, matchCase Ϊ�Ƿ����ô�Сд���в���
	 * 
	 * @param source
	 *            ��Ҫ�滻��Դ�ַ���
	 * @param oldStr
	 *            ��Ҫ���滻�����ַ���
	 * @param newStr
	 *            �滻Ϊ�����ַ���
	 * @param matchCase
	 *            �Ƿ���Ҫ���մ�Сд���з�ʽ����
	 */
	public static String replace(String source, String oldStr, String newStr,
			boolean matchCase) {
		if (source == null) {
			return null;
		}
		// ���ȼ����ַ����Ƿ����, �����ھͲ������滻
		if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
			return source;
		}
		int findStartPos = 0;
		int a = 0;
		while (a > -1) {
			int b = 0;
			String str1, str2, str3, str4, strA, strB;
			str1 = source;
			str2 = str1.toLowerCase();
			str3 = oldStr;
			str4 = str3.toLowerCase();
			if (matchCase) {
				strA = str1;
				strB = str3;
			} else {
				strA = str2;
				strB = str4;
			}
			a = strA.indexOf(strB, findStartPos);
			if (a > -1) {
				b = oldStr.length();
				findStartPos = a + b;
				StringBuffer bbuf = new StringBuffer(source);
				source = bbuf.replace(a, a + b, newStr) + "";
				// �µĲ��ҿ�ʼ��λ���滻����ַ����Ľ�β
				findStartPos = findStartPos + newStr.length() - b;
			}
		}
		return source;
	}

	/**
	 * ����ַ�����β�Ŀո�.
	 * 
	 * @param input
	 *            String ������ַ���
	 * @return ת�����
	 */
	public static String trimTailSpaces(String input) {
		if (isEmpty(input)) {
			return "";
		}

		String trimedString = input.trim();

		if (trimedString.length() == input.length()) {
			return input;
		}

		return input.substring(0,
				input.indexOf(trimedString) + trimedString.length());
	}

	/**
	 * Change the null string value to "", if not null, then return it self, use
	 * this to avoid display a null string to "null".
	 * 
	 * @param input
	 *            the string to clear
	 * @return the result
	 */
	public static String clearNull(String input) {
		return isEmpty(input) ? "" : input;
	}

	/**
	 * Return the limited length string of the input string (added at:April 10,
	 * 2004).
	 * 
	 * @param input
	 *            String
	 * @param maxLength
	 *            int
	 * @return String processed result
	 */
	public static String limitStringLength(String input, int maxLength) {
		if (isEmpty(input))
			return "";

		if (input.length() <= maxLength) {
			return input;
		} else {
			return input.substring(0, maxLength - 3) + "...";
		}

	}

	/**
	 * ���ַ���ת��Ϊһ�� javascript �� alert ����. eg: htmlAlert("What?"); returns <SCRIPT
	 * language="javascript">alert("What?")</SCRIPT>
	 * 
	 * @param message
	 *            ��Ҫ��ʾ����Ϣ
	 * @return ת�����
	 */
	public static String scriptAlert(String message) {
		return "<SCRIPT language=\"javascript\">alert(\"" + message
				+ "\");</SCRIPT>";
	}

	/**
	 * ���ַ���ת��Ϊһ�� javascript �� document.location �ı����. eg: htmlAlert("a.jsp");
	 * returns <SCRIPT language="javascript">document.location="a.jsp";</SCRIPT>
	 * 
	 * @param url
	 *            ��Ҫ��ʾ�� URL �ַ���
	 * @return ת�����
	 */
	public static String scriptRedirect(String url) {
		return "<SCRIPT language=\"javascript\">document.location=\"" + url
				+ "\";</SCRIPT>";
	}

	/**
	 * ���ؽű���� <SCRIPT language="javascript">history.back();</SCRIPT>
	 * 
	 * @return �ű����
	 */
	public static String scriptHistoryBack() {
		return "<SCRIPT language=\"javascript\">history.back();</SCRIPT>";
	}

	/**
	 * �˳������е�Σ�� HTML ����, ��Ҫ�ǽű�����, ������Ļ�����Լ��ű��¼��������
	 * 
	 * @param content
	 *            ��Ҫ�˳����ַ���
	 * @return ���˵Ľ��
	 */
	public static String replaceHtmlCode(String content) {
		if (isEmpty(content)) {
			return "";
		}
		// ��Ҫ�˳��Ľű��¼��ؼ���
		String[] eventKeywords = { "onmouseover", "onmouseout", "onmousedown",
				"onmouseup", "onmousemove", "onclick", "ondblclick",
				"onkeypress", "onkeydown", "onkeyup", "ondragstart",
				"onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
				"onrowexit", "onselectstart", "onload", "onunload",
				"onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
				"onscroll", "oncontextmenu" };
		content = replace(content, "<script", "&ltscript", false);
		content = replace(content, "</script", "&lt/script", false);
		content = replace(content, "<marquee", "&ltmarquee", false);
		content = replace(content, "</marquee", "&lt/marquee", false);
		content = replace(content, "/r/n", "<BR>");
		// �˳��ű��¼�����
		for (int i = 0; i < eventKeywords.length; i++) {
			content = replace(content, eventKeywords[i],
					"_" + eventKeywords[i], false); // ���һ��"_", ʹ�¼�������Ч
		}
		return content;
	}

	/**
	 * �˳� HTML ���� Ϊ�ı�����.
	 */
	public static String replaceHtmlToText(String input) {
		if (isEmpty(input)) {
			return "";
		}
		return setBr(setTag(input));
	}

	/**
	 * �˳� HTML ���. ��Ϊ XML ��ת���ַ���Ȼ��Ч, ��˰������ַ����˳����ĵ�ȫ���ַ�.
	 * 
	 * @author beansoft
	 * @param s
	 *            ������ִ�
	 * @return ���˺���ִ�
	 */
	public static String setTag(String s) {
		int j = s.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		char ch;
		for (int i = 0; i < j; i++) {
			ch = s.charAt(i);
			if (ch == '<') {
				// stringbuffer.append("<");
				stringbuffer.append("��");
			} else if (ch == '>') {
				// stringbuffer.append(">");
				stringbuffer.append("��");
			} else if (ch == '&') {
				// stringbuffer.append("&");
				stringbuffer.append("��");
			} else if (ch == '%') {
				// stringbuffer.append("%%");
				stringbuffer.append("��");
			} else {
				stringbuffer.append(ch);
			}
		}

		return stringbuffer.toString();
	}

	/** �˳� BR ���� */
	public static String setBr(String s) {
		int j = s.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		for (int i = 0; i < j; i++) {

			if (s.charAt(i) == '/' + 'n' || s.charAt(i) == '/' + 'r') {
				continue;
			} else {
				stringbuffer.append(s.charAt(i));
			}
		}

		return stringbuffer.toString();
	}

	/** �˳��ո� */
	public static String setNbsp(String s) {
		int j = s.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		for (int i = 0; i < j; i++) {
			if (s.charAt(i) == ' ') {
				stringbuffer.append(" ");
			} else {
				stringbuffer.append(s.charAt(i) + "");
			}
		}
		return stringbuffer.toString();
	}

	/**
	 * �ж��ַ����Ƿ�ȫ�������ַ�.
	 * 
	 * @param input
	 *            ������ַ���
	 * @return �жϽ��, true Ϊȫ����, false Ϊ���з������ַ�
	 */
	public static boolean isNumeric(String input) {
		if (isEmpty(input)) {
			return false;
		}

		for (int i = 0; i < input.length(); i++) {
			char charAt = input.charAt(i);

			if (!Character.isDigit(charAt)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ת���ɱ���ȡ�����ݵ�����(�� ISO8859 ת���� gb2312).
	 * 
	 * @param input
	 *            ������ַ���
	 * @return ת�����, ����д�����, �򷵻�ԭ����ֵ
	 */
	public static String toChi(String input) {
		try {
			byte[] bytes = input.getBytes("ISO8859-1");
			return new String(bytes, "GBK");
		} catch (Exception ex) {
		}
		return input;
	}

	/**
	 * ת���ɱ���ȡ�����ݵ����뵽 ISO(�� GBK ת����ISO8859-1).
	 * 
	 * @param input
	 *            ������ַ���
	 * @return ת�����, ����д�����, �򷵻�ԭ����ֵ
	 */
	public static String toISO(String input) {
		return changeEncoding(input, "GBK", "ISO8859-1");
	}

	/**
	 * ת���ַ���������.
	 * 
	 * @param input
	 *            ������ַ���
	 * @param sourceEncoding
	 *            Դ�ַ�������
	 * @param targetEncoding
	 *            Ŀ���ַ�������
	 * @return ת�����, ����д�����, �򷵻�ԭ����ֵ
	 */
	public static String changeEncoding(String input, String sourceEncoding,
			String targetEncoding) {
		if (input == null || input.equals("")) {
			return input;
		}

		try {
			byte[] bytes = input.getBytes(sourceEncoding);
			return new String(bytes, targetEncoding);
		} catch (Exception ex) {
		}
		return input;
	}

	/**
	 * �������� ' ���� ''; SQL ����:����������е��ַ�������һ��Ƕ�������,����ʹ�����������ű�ʾǶ��ĵ�����.
	 */

	public static String replaceSql(String input) {
		return replace(input, "'", "''");
	}

	/**
	 * �Ը����ַ����� URL ����
	 */
	public static String encode(String value) {
		if (isEmpty(value)) {
			return "";
		}

		try {
			value = java.net.URLEncoder.encode(value, "GB2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return value;
	}

	/**
	 * �Ը����ַ����� URL ����
	 * 
	 * @param value
	 *            ����ǰ���ַ���
	 * @return �������ַ���
	 */
	public static String decode(String value) {
		if (isEmpty(value)) {
			return "";
		}

		try {
			return java.net.URLDecoder.decode(value, "GB2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return value;
	}

	/**
	 * �ж��ַ����Ƿ�δ��, ���Ϊ null ���߳���Ϊ0, ������ true.
	 */
	public static boolean isEmpty(String input) {
		return (input == null || input.length() == 0);
	}

	/**
	 * ��������ַ������ֽڳ���(���������ֽ���), ���ڷ��Ͷ���ʱ�ж��Ƿ񳬳�����.
	 * 
	 * @param input
	 *            �����ַ���
	 * @return �ַ������ֽڳ���(���� Unicode ����)
	 */
	public static int getBytesLength(String input) {
		if (input == null) {
			return 0;
		}

		int bytesLength = input.getBytes().length;

		// System.out.println("bytes length is:" + bytesLength);

		return bytesLength;
	}

	/**
	 * �����ַ����Ƿ�δ��, �����, �򷵻ظ����ĳ�����Ϣ.
	 * 
	 * @param input
	 *            ������ַ���
	 * @param errorMsg
	 *            ������Ϣ
	 * @return �մ����س�����Ϣ
	 */
	public static String isEmpty(String input, String errorMsg) {
		if (isEmpty(input)) {
			return errorMsg;
		} else {
			return "";
		}
	}

	/**
	 * �õ��ļ�����չ��.
	 * 
	 * @param fileName
	 *            ��Ҫ������ļ�������.
	 * @return the extension portion of the file's name.
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}

	/**
	 * �õ��ļ���ǰ׺��.
	 * 
	 * @date 2005-10-18
	 * 
	 * @param fileName
	 *            ��Ҫ������ļ�������.
	 * @return the prefix portion of the file's name.
	 */
	public static String getPrefix(String fileName) {
		if (fileName != null) {
			fileName = fileName.replace("//", "/");

			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
			}

			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}

	/**
	 * �õ��ļ��Ķ�·��, ������Ŀ¼.
	 * 
	 * @date 2005-10-18
	 * 
	 * @param fileName
	 *            ��Ҫ������ļ�������.
	 * @return the short version of the file's name.
	 */
	public static String getShortFileName(String fileName) {
		if (fileName != null) {
			fileName = fileName.replace("//", "/");

			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
			}

			return fileName;
		}
		return "";
	}

	/**
	 * Gets the absolute pathname of the class or resource file containing the
	 * specified class or resource name, as prescribed by the current classpath.
	 * 
	 * @param resourceName
	 *            Name of the class or resource name.
	 * @return the absolute pathname of the given resource
	 */
	public static String getPath(String resourceName) {

		if (!resourceName.startsWith("/")) {
			resourceName = "/" + resourceName;
		}

		// resourceName = resourceName.replace('.', '/');

		java.net.URL classUrl = new StringUtil().getClass().getResource(
				resourceName);

		if (classUrl != null) {
			// System.out.println("/nClass '" + className +
			// "' found in /n'" + classUrl.getFile() + "'");
			// System.out.println("/n��Դ '" + resourceName +
			// "' ���ļ� /n'" + classUrl.getFile() + "' ���ҵ�.");

			return classUrl.getFile();
		} else {
			// System.out.println("/nClass '" + className +
			// "' not found in /n'" +
			// System.getProperty("java.class.path") + "'");
			// System.out.println("/n��Դ '" + resourceName +
			// "' û������·�� /n'" +
			// System.getProperty("java.class.path") + "' ���ҵ�");
			return null;
		}
	}

	/**
	 * ������ת��Ϊ���ı�ʾ��ʽ���ַ���(��ʽΪ yyyy��MM��dd�� HH:mm:ss).
	 */
	public static String dateToChineseString(Date date) {
		if (date == null) {
			return null;
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"yyyy��MM��dd�� HH:mm:ss");

		return dateformat.format(date);
	}

	/**
	 * ������ת��Ϊ 14 λ���ַ���(��ʽΪyyyyMMddHHmmss).
	 */
	public static String dateTo14String(Date date) {
		if (date == null) {
			return null;
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");

		return dateformat.format(date);
	}

	/**
	 * �� 14 λ���ַ���(��ʽΪyyyyMMddHHmmss)ת��Ϊ����.
	 */
	public static Date string14ToDate(String input) {
		if (isEmpty(input)) {
			return null;
		}

		if (input.length() != 14) {
			return null;
		}

		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");

		try {
			return dateformat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * �� TEXT �ı�ת��Ϊ HTML ����, �ѱ�����ҳ��ȷ����ʾ����.
	 * 
	 * @param input
	 *            ������ı��ַ���
	 * @return ת����� HTML ����
	 */
	public static String textToHtml(String input) {
		if (isEmpty(input)) {
			return "";
		}

		input = replace(input, "<", "<");
		input = replace(input, ">", ">");

		input = replace(input, "/n", "<br>/n");
		input = replace(input, "/t", "    ");
		input = replace(input, "  ", "  ");

		return input;
	}

	// ----------------------------------------------------------------------------------------------

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param str
	 *            null���� ������null��������true
	 * @return
	 */
	public static boolean isNullString(String str) {
		return (null == str || StringUtil.isBlank(str.trim()) || "null"
				.equals(str.trim().toLowerCase())) ? true : false;
	}

	/**
	 * ��ʽ���ַ��� ���Ϊ�գ����ء���
	 * 
	 * @param str
	 * @return
	 */
	public static String formatString(String str) {
		if (isNullString(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * ��ȡ�ַ�������ĸ�����ֶ����ԣ����ֲ����ȡ��
	 * 
	 * @param str
	 *            �ַ���
	 * @param n
	 *            ��ȡ�ĳ��ȣ���ĸ�������Ϊ���֣�һ�����ֵ���������ĸ��
	 * @return
	 */
	public static String subStringByByte(String str, int n) {
		int num = 0;
		try {
			byte[] buf = str.getBytes("GBK");
			if (n >= buf.length) {
				return str;
			}
			boolean bChineseFirstHalf = false;
			for (int i = 0; i < n; i++) {
				if (buf[i] < 0 && !bChineseFirstHalf) {
					bChineseFirstHalf = true;
				} else {
					num++;
					bChineseFirstHalf = false;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.substring(0, num);
	}

}