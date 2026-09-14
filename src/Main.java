/*
 * 1. Приложение должно быть реализовано в форме консольного интерфейса
 * (CLI). +
 * 2. Приглашение к вводу должно формироваться на основе реальных данных
 * ОС, в которой исполняется эмулятор. Пример: username@hostname:~$. +
 * 3. Реализовать парсер, который поддерживает раскрытие переменных
 * окружения реальной ОС (например, $HOME). +
 * 4. Сообщить об ошибке выполнения команд (неизвестная команда, неверные
 * аргументы). +
 * 5. Реализовать команды-заглушки, которые выводят свое имя и аргументы: ls,
 * cd. +
 * 6. Реализовать команду exit. +
 * 7. Продемонстрировать работу прототипа в интерактивном режиме. +
 * Необходимо показать примеры работы всей реализованной
 * функциональности, включая обработку ошибок.
 * 8. Результат выполнения этапа сохранить в репозиторий стандартно
 * оформленным коммитом. +
 */

void main() throws UnknownHostException {
    Scanner scanner = new Scanner(System.in);

    while (true) {
        IO.print(getWelcomeMessage());

        if (!scanner.hasNextLine()) break;

        String line = scanner.nextLine();
        List<String> input = parseUserInput(line);

        if (input.isEmpty()) continue;

        String command = input.getFirst();
        List<String> cmdArgs = input.subList(1, input.size());

        if (command.equals("exit")) break;

        executeCommand(command, cmdArgs);
    }
}

// Генерация приветственного сообщения
public static String getWelcomeMessage() throws UnknownHostException {
    String username = System.getProperty("user.name");
    String hostName = InetAddress.getLocalHost().getHostName();
    return String.format("%s@%s:~$ ", username, hostName);
}

// Парсинг строки и раскрытие переменных окружения
public static List<String> parseUserInput(String line) {
    String trimmed = line.trim();
    if (trimmed.isEmpty()) {
        return List.of();
    }

    String[] inputList = trimmed.split("\\s+");
    List<String> parsedInput = new ArrayList<>();

    for (String token : inputList) {
        if (token.startsWith("$") && token.length() > 1) {
            String varName = token.substring(1);
            String envVal = System.getenv(varName);

            parsedInput.add(envVal != null ? envVal : "");
        } else {
            parsedInput.add(token);
        }
    }
    return parsedInput;
}

// Проверка и обработка ошибок ввода
public static void executeCommand(String command, List<String> cmdArgs) {

    if (cmdArgs.isEmpty()) System.out.println("Неверные аргументы");
    else System.out.printf("Команда: %s, аргументы: %s%n", command, cmdArgs);
}