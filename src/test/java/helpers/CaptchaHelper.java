package helpers;

public class CaptchaHelper {

    public static String doMathCaptcha(String equationString)
    {
        String resultString = "";
        if(equationString.contains("+"))
        {
            String[] parts = equationString.split("\\+");
            int mathValue = Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
            resultString = Integer.toString(mathValue);
        }
        else if(equationString.contains("-"))
        {
            String[] parts = equationString.split("\\-");
            int mathValue = Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]);
            resultString = Integer.toString(mathValue);
        }
        else if(equationString.contains("*"))
        {
            String[] parts = equationString.split("\\*");
            int mathValue = Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
            resultString = Integer.toString(mathValue);
        }
        return resultString;
    }

    public static String doTextCaptcha(String captchaString)
    {
        StringBuilder sb = new StringBuilder(captchaString);
        return sb.reverse().toString();
    }

}
