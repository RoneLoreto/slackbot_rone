import java.io.IOException;
import java.util.Calendar;

import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.riversun.xternal.simpleslackapi.SlackChannel;

public class Chatbot {
    public static void main(String[] args) throws IOException {
        // 時刻取得
        Calendar cTime = Calendar.getInstance();
        String[] dWeek = {"日", "月", "火", "水", "木", "金", "土"}; //曜日の設定

        // Slack API token
        String botToken ="xoxb-410080365444-520678305543-dqWVP5FDNuKxIZ2dAwwPglsy" ;

        SlackletService slackService = new SlackletService(botToken);

        // slackletの追加
        slackService.addSlacklet(new Slacklet() {

            @Override
            public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {

                // メッセージがポストされたチャンネルを取得する
                SlackChannel channel = req.getChannel();

                if ("chatbot".equals(channel.getName())) {
                    // #chatbotチャンネル

                    // メッセージ本文を取得
                    String content = req.getContent();

                    switch (content) {
                        case "おはよう":
                        case "おはよ":
                        case "起きた":
                        case "起":
                            resp.reply("おはよう");
                            break;

                        case "Hello":
                        case "hello":
                        case "こんにちは":
                            resp.reply("こんにちは");
                            break;

                        case "今何時":
                        case "何時":
                            resp.reply(+ cTime.get(Calendar.HOUR_OF_DAY) + "時" + cTime.get(Calendar.MINUTE) + "分だよ");
                            break;

                        case "今日は何日":
                        case "今日は":
                            resp.reply(+ cTime.get(Calendar.MONTH) + 1 + "月" + cTime.get(Calendar.DAY_OF_MONTH) + "日 (" + dWeek[cTime.get(Calendar.DAY_OF_WEEK) - 1] + ")");
                            break;

                        case "今年は何年":
                        case "今年は":
                        case "今年は何年？":
                        case "今年は何年?":
                            resp.reply(+ cTime.get(Calendar.YEAR) + "年だよ");
                            break;

                        case "今年は残り":
                        case "今年の残り日数":
                            if ((cTime.get(Calendar.YEAR) % 4 == 0) && (cTime.get(Calendar.YEAR) % 100 == 0) && (cTime.get(Calendar.YEAR) % 400 == 0)) {
                                resp.reply(+ 366 - cTime.get(Calendar.DAY_OF_YEAR) + "日だよ");
                            } else {
                                resp.reply(+ 365 - cTime.get(Calendar.DAY_OF_YEAR) + "日だよ");
                            }
                            break;

                        default:
                            resp.reply("なんて言いました？");

                    }

                }

            }
        });

        // Slackに接続
        slackService.start();

    }

}
