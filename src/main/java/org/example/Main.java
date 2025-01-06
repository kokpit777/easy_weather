package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("enter city(ru/eng): ");
        String city = in.nextLine();

        String key = "d4d85bd3b56543722d65fac084ef5caf"; //апи ключ с сайта с погодой
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru"; // ссылка с сайта + хуйня которая город введенный подставляет и я еще сделал отдельно ключ чтобы абузить систему если надо, хз как работает

        try {
            URL apiUrl = new URL(url);//я не знаю как оно действует почему try?? хуй пойми бля

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();//тут оно вроде к сайту подключается но я не понимаю схуя, типа где это указано
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();//проверяет код ответа
            if (responseCode == HttpURLConnection.HTTP_OK) { //код 200 это значит что все хорошо запрос поехал
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());//пирсинг с погоды
                String weather = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
                double temp = jsonResponse.getJSONObject("main").getDouble("temp");

                System.out.println("Погода: " +city + " " +weather); //вывод
                System.out.println("Температура: " + temp + "  градусов");
            } else {
                System.out.println("не удалось получить данные " + responseCode);
            }
        } catch (Exception e) {//эту часть мне гпт доделал я не знаю нахуя это но без не работает
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}