package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.entity.communication.Alarm;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmSender {

  private final JavaMailSender mailSender;

  public void send(Alarm alarm, List<EventAttendee> eventAttendees) {
    log.info("Sending alarm: {} (event: {})", alarm.getAlarmId(), alarm.getEvent().getEventId());

    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper =
          new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

      helper.setTo(
          eventAttendees.stream()
              .map(EventAttendee::getUser)
              .map(User::getEmail)
              .toArray(String[]::new));
      helper.setSubject("IHub 이벤트 리마인더");
      helper.setText(
          String.format(
              HTML_FORMAT,
              alarm.getEvent().getTitle(),
              alarm.getEvent().getProject().getTitle(),
              alarm.getEvent().getStartDatetime(),
              alarm.getEvent().getEndDatetime(),
              alarm.getEvent().getLocation(),
              alarm.getEvent().getContent(),
              alarm.getEvent().getCreatedBy().getName(),
              alarm.getEvent().getCreatedBy().getEmail()
          ));
    } catch (MessagingException e) {
      throw new IllegalStateException("메일 전송 실패", e);
    }
  }

  private static final String HTML_FORMAT = """
      <!DOCTYPE html>
      <html lang="ko">
      <head>
        <meta charset="UTF-8">
        <title>이벤트 알림</title>
      </head>
      <body style="margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, sans-serif;">
      
        <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:20px 0;">
          <tr>
            <td align="center">
              <table width="600" cellpadding="0" cellspacing="0"
                     style="background-color:#ffffff; border-radius:8px; box-shadow:0 2px 8px rgba(0,0,0,0.05);">
      
                <!-- Header -->
                <tr>
                  <td style="padding:24px 32px; border-bottom:1px solid #e5e7eb;">
                    <h2 style="margin:0; font-size:20px; color:#111827;">
                      📅 이벤트 알림
                    </h2>
                    <p style="margin:8px 0 0; font-size:14px; color:#6b7280;">
                      예정된 이벤트가 곧 시작됩니다.
                    </p>
                  </td>
                </tr>
      
                <!-- Body -->
                <tr>
                  <td style="padding:24px 32px;">
      
                    <table width="100%%" cellpadding="0" cellspacing="0" style="font-size:14px; color:#374151;">
                      <tr>
                        <td style="padding:8px 0; font-weight:bold; width:140px;">이벤트 제목</td>
                        <td style="padding:8px 0;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding:8px 0; font-weight:bold;">프로젝트명</td>
                        <td style="padding:8px 0;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding:8px 0; font-weight:bold;">시작 시간</td>
                        <td style="padding:8px 0;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding:8px 0; font-weight:bold;">종료 시간</td>
                        <td style="padding:8px 0;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding:8px 0; font-weight:bold;">위치</td>
                        <td style="padding:8px 0;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding:8px 0; font-weight:bold; vertical-align:top;">이벤트 내용</td>
                        <td style="padding:8px 0; line-height:1.6;">
                          %s
                        </td>
                      </tr>
                    </table>
      
                    <hr style="border:none; border-top:1px solid #e5e7eb; margin:24px 0;">
      
                    <p style="margin:0; font-size:13px; color:#6b7280;">
                      이 이벤트는 <strong>%s</strong> (%s) 님이 생성했습니다.
                    </p>
      
                  </td>
                </tr>
      
                <!-- Footer -->
                <tr>
                  <td style="padding:16px 32px; background-color:#f9fafb; border-top:1px solid #e5e7eb;">
                    <p style="margin:0; font-size:12px; color:#9ca3af; text-align:center;">
                      본 메일은 자동 발송 메일입니다.
                    </p>
                  </td>
                </tr>
      
              </table>
            </td>
          </tr>
        </table>
      
      </body>
      </html>
      """;
}
