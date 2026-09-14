/*
 * 1. Приложение должно быть реализовано в форме консольного интерфейса
 * (CLI).
 * 2. Приглашение к вводу должно формироваться на основе реальных данных
 * ОС, в которой исполняется эмулятор. Пример: username@hostname:~$. +
 * 3. Реализовать парсер, который поддерживает раскрытие переменных
 * окружения реальной ОС (например, $HOME).
 * 4. Сообщить об ошибке выполнения команд (неизвестная команда, неверные
 * аргументы).
 * 5. Реализовать команды-заглушки, которые выводят свое имя и аргументы: ls,
 * cd.
 * 6. Реализовать команду exit. +
 * 7. Продемонстрировать работу прототипа в интерактивном режиме.
 * Необходимо показать примеры работы всей реализованной
 * функциональности, включая обработку ошибок.
 * 8. Результат выполнения этапа сохранить в репозиторий стандартно
 * оформленным коммитом.
 */

void main() throws UnknownHostException {
    while (true) {
        String welcomeMessage = getWelcomeMessage();
        System.out.print(welcomeMessage);

        Scanner scanner = new Scanner(System.in);

        if (scanner.nextLine().equals("exit")) break;

        System.out.println(parseUserInput(scanner));
    }
}

// Генерация приветственного сообщения для пользователя
public static String getWelcomeMessage() throws UnknownHostException {
    String username = System.getProperty("user.name");
    String hostName = InetAddress.getLocalHost().getHostName();

    return String.format("%s@%s:~$ ", username, hostName);
}

// Получение и парсинг пользовательского ввода
public static String parseUserInput(Scanner scanner) {
    String line = scanner.nextLine().trim();
    if (line.isEmpty()) {
        return "";
    }

    String[] tokens = line.split("\\s+");
    List<String> outputList = new ArrayList<>();

    for (String token : tokens) {
        if (token.startsWith("$") && token.length() > 1) {
            String envVal = System.getenv(token.substring(1));
            outputList.add(envVal != null ? envVal : "");
        } else {
            outputList.add(token);
        }
    }

    return String.join(" ", outputList);
}