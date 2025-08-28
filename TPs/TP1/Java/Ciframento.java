class Ciframento {

    // método que aplica a Cifra de César em uma string
    public static String Cifra(String str) {
        String palavra = ""; // string que vai armazenar o resultado cifrado

        // percorre cada caractere da string
        for (int i = 0; i < str.length(); i++) {
            char c = (char)(str.charAt(i) + 3); // aplica o deslocamento de 3
            palavra += c;
        }

        return palavra;
    }

    public static void main(String[] args) {
        String str = MyIO.readLine(); // lê a primeira linha de entrada

        // repete enquanto a entrada não for "FIM"
        while (!(str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M')) {
            String palavra = Cifra(str);  // chama o método Cifra para cifrar a string
            MyIO.println(palavra);        // imprime a palavra
            str = MyIO.readLine();        // lê a próxima linha para continuar
        }
    }
}