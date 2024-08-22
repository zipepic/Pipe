package com.example.shipmentservicelight.letcode;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class MyTelegramBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "7222738116:AAEDbC87lJ_ILn4Y5XbjUAtWomA9ruboW-8"; // Замените на ваш токен
    private final String BOT_USERNAME = "PodpivasnikiVPNBot"; // Замените на имя вашего бота
    private final String CHAT_ID = "-1001846098445"; // Замените на нужный chat ID

    private Map<String, String> previousState = new HashMap<>();

    private List<Device> state = new ArrayList<>();

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Этот метод будет вызываться при каждом обновлении от Telegram
        // Здесь можно обрабатывать входящие сообщения
    }

    public void sendMessageToChat(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String executePythonScript() {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("python3 /etc/wireguard/online11.py");
//            Process process = Runtime.getRuntime().exec("cat /Users/xzz1p/Downloads/botTg/test.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

//    public Map<String, String> parseOutput(String output) {
//        Map<String, String> state = new HashMap<>();
//        String[] lines = output.split("\n");
//        StringBuilder peerData = new StringBuilder();
//
//        String peerName = null;
//        for (String line : lines) {
//            if (line.startsWith("Active") || line.startsWith("Inactive")) {
//                if (peerName != null) {
//                    state.put(peerName, peerData.toString());
//                }
//                peerName = line.split(" ")[1].replace(":", "");
//                peerData = new StringBuilder();
//            }
//            if (peerName != null) {
//                peerData.append(line).append("\n");
//            }
//        }
//        if (peerName != null) {
//            state.put(peerName, peerData.toString());
//        }
//        return state;
//    }

    public String updateDetect(List<Device> newDeviceList){
        StringBuilder changes = new StringBuilder();
        for(Device newDevice: newDeviceList){
            if (findByName(state,newDevice.name) == null){
                changes.append("New peer detected: \n").append(newDevice);
            }else {
                if(!newDevice.equals(findByName(state,newDevice.name))){
                    changes.append("Peer "+ newDevice.name+" updated: \n").append(findByName(state,newDevice.name).changengedField(newDevice));
                }
            }
            for(Device old: state){
                if( findByName(newDeviceList,old.name) == null){
                    changes.append("Peer removed: \n").append(old);
                }
            }
        }
        return changes.toString();
    }
    public Device findByName(List<Device> list,String name){
        for(Device d: list){
            if(d.name.equals(name))
                return  d;
        }
        return null;
    }

//    public String detectChanges(Map<String, String> previousState, Map<String, String> currentState) {
//        StringBuilder changes = new StringBuilder();
//        for (String key : currentState.keySet()) {
//            if (!previousState.containsKey(key)) {
//                changes.append("New peer detected: \n").append(currentState.get(key)).append("\n");
//            } else if (!previousState.get(key).equals(currentState.get(key))) {
//
//                changes.append("Peer updated: \n").append(currentState.get(key)).append("\n");
//            }
//        }
//        for (String key : previousState.keySet()) {
//            if (!currentState.containsKey(key)) {
//                changes.append("Peer removed: \n").append(previousState.get(key)).append("\n");
//            }
//        }
//        return changes.toString();
//    }

    public static void main(String[] args) {
        MyTelegramBot bot = new MyTelegramBot();

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(ScheduledJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1) // Интервал в 1 минуту
                            .repeatForever())
                    .build();

            scheduler.getContext().put("bot", bot);
            scheduler.getContext().put("chatId", bot.CHAT_ID);

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }

        // Отправляем начальную статистику при запуске
        String initialOutput = bot.executePythonScript();

        bot.state = DeviceParser.parseDevices(initialOutput);
        //bot.previousState = bot.parseOutput(initialOutput);
        bot.sendMessageToChat(bot.CHAT_ID, "Bot started \n" + initialOutput);
    }

    public static class ScheduledJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            try {
                MyTelegramBot bot = (MyTelegramBot) context.getScheduler().getContext().get("bot");
                String chatId = (String) context.getScheduler().getContext().get("chatId");

                String currentOutput = bot.executePythonScript();
                //Map<String, String> currentState = bot.parseOutput(currentOutput);

                List<Device> newState = DeviceParser.parseDevices(currentOutput);

                //String changes = bot.detectChanges(bot.previousState, currentState);
                String changes = bot.updateDetect(newState);
                if (!changes.isEmpty()) {
                    bot.sendMessageToChat(chatId, changes);
                }
                bot.state = newState;
                //bot.previousState = currentState;
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}
