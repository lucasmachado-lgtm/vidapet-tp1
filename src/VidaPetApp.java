import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VidaPet {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        String nomeAnimal = "Eva";
        String especieAnimal = "Gato";
        String tutorAnimal = "Lucas Machado";
        String nomeVeterinario = "Doutora Ana";
        String crmvVeterinario = "SP-12345";

        // Variáveis de controle da consulta simulada
        LocalDate dataConsulta = null;
        LocalTime horarioConsulta = null;
        String statusConsulta = "Nenhuma consulta agendada";
        String observacoesConsulta = "";

        System.out.println("==================================================");
        System.out.println("   Bem-vindo ao Sistema de Agendamento VidaPet   ");
        System.out.println("==================================================");

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Animal e Veterinário");
            System.out.println("2. Agendar Consulta");
            System.out.println("3. Visualizar Agenda");
            System.out.println("4. Realizar Consulta (Área do Veterinário)");
            System.out.println("5. Cancelar Consulta");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.println("\n[Cadastro Realizado com Sucesso]");
                        System.out.println("Animal: " + nomeAnimal + " (" + especieAnimal + ") | Tutor: " + tutorAnimal);
                        System.out.println("Veterinário: " + nomeVeterinario + " | CRMV: " + crmvVeterinario);
                        break;

                    case 2:
                        System.out.println("\n--- NOVO AGENDAMENTO ---");
                        System.out.print("Digite a data da consulta (DD/MM/AAAA): ");
                        String dataStr = scanner.nextLine();

                        System.out.print("Digite o horário da consulta (HH:MM - ex: 14:00): ");
                        String horaStr = scanner.nextLine();

                        try {
                            LocalDate dataDigitada = LocalDate.parse(dataStr, DATE_FORMATTER);
                            LocalTime horaDigitada = LocalTime.parse(horaStr, TIME_FORMATTER);

                            if (dataDigitada.isBefore(LocalDate.now())) {
                                System.out.println(" Erro: Não é permitido agendar consultas em datas passadas.");
                                break;
                            }

                            // Regra 1: Horário comercial (8h às 18h) e duração de 30 min (ex: final até 18:00)
                            LocalTime inicioExpediente = LocalTime.of(8, 0);
                            LocalTime fimExpediente = LocalTime.of(18, 0);

                            if (horaDigitada.isBefore(inicioExpediente) || horaDigitada.isAfter(fimExpediente.minusMinutes(30))) {
                                System.out.println(" Erro: Horário inválido. O atendimento ocorre de segunda a sexta, das 8h às 18h[cite: 1].");
                                break;
                            }

                            // Salvando o agendamento
                            dataConsulta = dataDigitada;
                            horarioConsulta = horaDigitada;
                            statusConsulta = "Agendada";
                            observacoesConsulta = "Pendente de atendimento";

                            System.out.println("✔ Sucesso! Consulta agendada para " + dataConsulta.format(DATE_FORMATTER)
                                    + " às " + horarioConsulta.format(TIME_FORMATTER) + " com " + nomeVeterinario + ".");

                        } catch (DateTimeParseException e) {
                            System.out.println(" Erro: Formato de data ou hora inválido. Use o padrão correto.");
                        }
                        break;

                    case 3:
                        System.out.println("\n--- AGENDA ATUAL ---");
                        System.out.println("Animal: " + nomeAnimal);
                        System.out.println("Veterinário: " + nomeVeterinario);
                        if (dataConsulta != null && horarioConsulta != null) {
                            System.out.println("Data: " + dataConsulta.format(DATE_FORMATTER));
                            System.out.println("Horário: " + horarioConsulta.format(TIME_FORMATTER));
                        } else {
                            System.out.println("Data/Horário: Não definidos");
                        }
                        System.out.println("Situação: " + statusConsulta);
                        System.out.println("Observações: " + observacoesConsulta);
                        break;

                    case 4:
                        System.out.println("\n--- REALIZAR CONSULTA (Área do Veterinário) ---");
                        if ("Agendada".equals(statusConsulta)) {
                            System.out.print("Digite as observações do atendimento: ");
                            observacoesConsulta = scanner.nextLine();
                            statusConsulta = "Realizada";
                            System.out.println("✔ Consulta marcada como REALIZADA com sucesso!");
                        } else {
                            System.out.println(" Erro: Somente consultas com status 'Agendada' podem ser marcadas como realizadas].");
                        }
                        break;

                    case 5:
                        System.out.println("\n--- CANCELAR CONSULTA ---");
                        if ("Agendada".equals(statusConsulta)) {
                            statusConsulta = "Cancelada";
                            System.out.println("✔ Consulta cancelada com sucesso.");
                        } else {
                            System.out.println(" Erro: A consulta só pode ser cancelada se estiver com o status 'Agendada'.");
                        }
                        break;

                    case 6:
                        System.out.println("Encerrando o sistema VidaPet. Até logo!");
                        break;

                    default:
                        System.out.println(" Opção inválida. Escolha um número de 1 a 6.");
                }
            } else {
                System.out.println(" Entrada inválida. Digite um número inteiro.");
                scanner.next();
            }

        } while (opcao != 6);

        scanner.close();
    }
}
