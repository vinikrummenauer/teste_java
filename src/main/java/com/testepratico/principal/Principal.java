package com.testepratico.principal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vinicius
 */
public class Principal {

    public static void main(String[] args) {
        // 3.1 Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3071.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 Remover o funcionário "João"
        Funcionario funcionarioJoao = null;
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getNome().equals("João")) {
                funcionarioJoao = funcionario;
                break;
            }
        }
        if (funcionarioJoao != null) {
            funcionarios.remove(funcionarioJoao);
        }

        // 3.3 Imprimir todos os funcionários
        System.out.println("Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome()
                    + ", Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + ", Salário: " + formatarValor(funcionario.getSalario())
                    + ", Função: " + funcionario.getFuncao());
        }

        // 3.4 Aumentar salário em 10%
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(BigDecimal.valueOf(1.1));
            funcionario.setSalario(novoSalario);
        }

        // 3.5 Agrupar funcionários por função em um Map
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            funcionariosPorFuncao.computeIfAbsent(funcao, k -> new ArrayList<>()).add(funcionario);
        }

        // 3.6 – Imprimir os funcionários, agrupados por função.
        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("  Nome: " + funcionario.getNome()
                        + ", Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + ", Salário: " + formatarValor(funcionario.getSalario()));
            }
        }

        // 3.8 Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        for (Funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            if (mesAniversario == 10 || mesAniversario == 12) {
                System.out.println("Nome: " + funcionario.getNome()
                        + ", Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }

        // 3.9 Imprimir o funcionário com a maior idade
        Funcionario maisVelho = null;
        int maiorIdade = 0;
        for (Funcionario funcionario : funcionarios) {
            int idade = Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears();
            if (idade > maiorIdade) {
                maiorIdade = idade;
                maisVelho = funcionario;
            }
        }

        if (maisVelho != null) {
            System.out.println("\nFuncionário mais velho:");
            System.out.println("Nome: " + maisVelho.getNome()
                    + ", Idade: " + maiorIdade);
        } else {
            System.out.println("\nNão há funcionários.");
        }

        // 3.10 Imprimir a lista de funcionários por ordem alfabética
        funcionarios.sort((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()));
        System.out.println("\nLista de funcionários por ordem alfabética:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
        }

        // 3.11 Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        System.out.println("\nTotal dos salários dos funcionários: " + formatarValor(totalSalarios));

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário
        int salarioMinimo = 1212;
        System.out.println("\nSalários mínimos ganhos por cada funcionário:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioFuncionario = funcionario.getSalario();
            int salariosMinimos = salarioFuncionario.divide(BigDecimal.valueOf(salarioMinimo), 0, BigDecimal.ROUND_DOWN).intValue();
            System.out.println("Nome: " + funcionario.getNome() + ", Salários Mínimos: " + salariosMinimos);
        }

    }

    public static String formatarValor(BigDecimal valor) {
        NumberFormat formatoMoeda = DecimalFormat.getCurrencyInstance(new Locale("pt", "BR"));
        formatoMoeda.setMinimumFractionDigits(2);
        return formatoMoeda.format(valor);
    }

}
