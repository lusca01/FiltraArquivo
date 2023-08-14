import java.io.*;

public class FiltraDados {

    public static void main(String[] args) {
        String path = "D:\\ProjetosJava\\src";
        String nome = "teste.txt";
        try {
            readFile(path, nome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String path, String nome) throws IOException {
        File arq = new File(path, nome);
        if(arq.exists() && arq.isFile()) {
            FileInputStream fluxo = new FileInputStream(arq);
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha = buffer.readLine();
            System.out.println("TABLE NAME \t FISICO \t TIPO \t PRECISAO \t OBRIGATORIO");
            while(linha != null) {
                if(linha.contains("CREATE TABLE")) {
                    String nomeT = nomeTabela(linha);
                    linha = buffer.readLine();
                    while (!linha.contains("CONSTRAINT")){
                        if (!(linha.contains("varchar") || linha.contains("char") || linha.contains("decimal"))){
                            aux2(linha, nomeT);
                        } else {
                            aux(linha, nomeT);
                        }
                        linha = buffer.readLine();
                    }
                }
                linha = buffer.readLine();
            }
            buffer.close();
            leitor.close();
            fluxo.close();
        }else {
            throw new IOException("Arquivo Inválido");
        }
    }

    public static void aux2(String frase, String nomeT){
        String[] vetorPalavras = frase.split("[\\[\\]()]");
        System.out.println(nomeT + "\t" + vetorPalavras[1] + "\t" +vetorPalavras[3] +
                " " + "\t" + "\t" + vetorPalavras[4]);
    }

    public static void aux(String frase, String nomeT){
        String[] vetorPalavras = frase.split("[\\[\\]()]");
        System.out.println(nomeT + "\t" + vetorPalavras[1] + "\t" + vetorPalavras[3] +
                "\t" + vetorPalavras[5] + "\t" + vetorPalavras[6]);
    }

    public static String nomeTabela(String frase) {
        String[] nomeTabela = frase.split("[\\[\\]()]");
        return nomeTabela[3];
    }
}