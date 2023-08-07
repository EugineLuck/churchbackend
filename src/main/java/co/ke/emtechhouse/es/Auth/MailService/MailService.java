package co.ke.emtechhouse.es.Auth.MailService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class MailService {
    //@Value("${mail.host}")
    private String host= "mail.emtechhouse.co.ke";
//    @Value("${mail.port}")
    private String port ="587";
//    @Value("${mail.password}")
    private String password="Pass123$$";
//    @Value("${mail.username}")
    private String sender="no-reply@emtechhouse.co.ke";
//    @Value("${organization.image_banner}")
    private String company_logo_path="src/main/resources/logo.png";
//    @Value("${organization.image_banner}")
    private String banner_path="src/main/resources/logo.png";

    static int year = Calendar.getInstance().get(Calendar.YEAR);

    public void sendNotification(String to, String body, String subject) throws MessagingException, IOException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(sender);
            helper.setSubject(subject);
//            DataSource report = new FileDataSource(filename);

            helper.setText("<!DOCTYPE html>\n" +
                    "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                    "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                    "  <title></title>\n" +
                    "  <!--[if mso]>\n" +
                    "  <style>\n" +
                    "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                    "    div, td {padding:0;}\n" +
                    "    div {margin:0 !important;}\n" +
                    "  </style>\n" +
                    "  <noscript>\n" +
                    "    <xml>\n" +
                    "      <o:OfficeDocumentSettings>\n" +
                    "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                    "      </o:OfficeDocumentSettings>\n" +
                    "    </xml>\n" +
                    "  </noscript>\n" +
                    "  <![endif]-->\n" +
                    "  <style>\n" +
                    "    table, td, div, h1, p {\n" +
                    "      font-family: Arial, sans-serif;\\n\"\n" +
                    "    }\n" +
                    "    @media screen and (max-width: 530px) {\n" +
                    "      .unsub {\n" +
                    "        display: block;\n" +
                    "        padding: 8px;\n" +
                    "        margin-top: 14px;\n" +
                    "        border-radius: 6px;\n" +
                    "        background-color: #555555;\n" +
                    "        text-decoration: none !important;\n" +
                    "        font-weight: bold;\n" +
                    "      }\n" +
                    "      .col-lge {\n" +
                    "        max-width: 100% !important;\n" +
                    "      }\n" +
                    "    }\n" +
                    "    @media screen and (min-width: 531px) {\n" +
                    "      .col-sml {\n" +
                    "        max-width: 27% !important;\n" +
                    "      }\n" +
                    "      .col-lge {\n" +
                    "        max-width: 73% !important;\n" +
                    "      }\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body style=\"margin:0;padding:0;word-spacing:normal;background-color: #566fff;\">\n" +
                    "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#566fff;\">\n" +
                    "    <table role=\"presentation\" style=\"width:100%; padding-top: 10px; padding-bottom: 10px; border:none;border-spacing:0;\">\n" +
                    "      <tr>\n" +
                    "        <td align=\"center\" style=\"padding:0;\">\n" +
                    "          <!--[if mso]>\n" +
                    "          <table role=\"presentation\" align=\"center\" style=\"width:600px; margin-top: 10px; margin-bottom: 10px;\">\n" +
                    "          <tr>\n" +
                    "          <td>\n" +
                    "          <![endif]-->\n" +
                    "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                    "              <td style=\"padding:5px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                    "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:companyLogo' alt=\"Logo\" style=\"width:20%; text-align:center; margin:auto; height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                    "                <hr>\n" +
                    "              </td>\n" +
                    "            <tr>\n" +
                    "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                    "                 <h2 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">" + subject + "</h2>\n" + body +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                    "                       <img src='cid:rightSideImage' style='width:100%;'/>" +
                    "              </td>\n" +
                    "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                    "              </td>\n" +
                    "            <tr>\n" +
                    "            </tr>\n" +
                    "           \n" +
                    "            <tr>\n" +
                    "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
                    "              <p style=\"margin:0;font-size:14px;line-height:20px;\"> &copy;<b>Copyright " + year + ".</b><br></p>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </table>\n" +
                    "          <!--[if mso]>\n" +
                    "          </td>\n" +
                    "          </tr>\n" +
                    "          </table>\n" +
                    "          <![endif]-->\n" +
                    "        </td>\n" +
                    "      </tr>\n" +
                    "    </table>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>", true);
            helper.addInline("companyLogo", new File(company_logo_path));
            helper.addInline("rightSideImage", new File(banner_path));
//            helper.addAttachment("CardReport_" + df.format(new Date()) + ".pdf", report);
            System.out.println("Mail sent successfully to:" + to);
//            log.info("Sent successfully,sent from: {}", to);
            Transport.send(message);
//            log.info("{ OK } Email Sent Successfully to { " + recipientemail + " }");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("{ ERROR } Sending Email to { "+ to +" }");
        }
    }
}