package org.example;

public class Main<K, V> {
    private static final int TAMANHO_INICIAL = 10;
    private Object[] chaves;
    private Object[] valores;
    private int tamanho;

    public TabelaHash() {
        chaves = new Object[TAMANHO_INICIAL];
        valores = new Object[TAMANHO_INICIAL];
        tamanho = 0;
    }

    private int hash(K chave) {
        return Math.abs(chave.hashCode()) % chaves.length;
    }

    public void inserir(K chave, V valor) {
        if (tamanho >= chaves.length * 0.75) {
            redimensionar();
        }
        int indice = hash(chave);
        while (chaves[indice] != null) {
            if (chaves[indice].equals(chave)) {
                valores[indice] = valor;
                return;
            }
            indice = (indice + 1) % chaves.length;
        }
        chaves[indice] = chave;
        valores[indice] = valor;
        tamanho++;
    }

    public V buscar(K chave) {
        int indice = hash(chave);
        while (chaves[indice] != null) {
            if (chaves[indice].equals(chave)) {
                return (V) valores[indice];
            }
            indice = (indice + 1) % chaves.length;
        }
        return null;
    }

    public void remover(K chave) {
        int indice = hash(chave);
        while (chaves[indice] != null) {
            if (chaves[indice].equals(chave)) {
                chaves[indice] = null;
                valores[indice] = null;
                tamanho--;
                return;
            }
            indice = (indice + 1) % chaves.length;
        }
    }

    private void redimensionar() {
        int novoTamanho = chaves.length * 2;
        Object[] novasChaves = new Object[novoTamanho];
        Object[] novosValores = new Object[novoTamanho];
        for (int i = 0; i < chaves.length; i++) {
            if (chaves[i] != null) {
                int novoIndice = Math.abs(chaves[i].hashCode()) % novoTamanho;
                while (novasChaves[novoIndice] != null) {
                    novoIndice = (novoIndice + 1) % novoTamanho;
                }
                novasChaves[novoIndice] = chaves[i];
                novosValores[novoIndice] = valores[i];
            }
        }
        chaves = novasChaves;
        valores = novosValores;
    }

    public static void main(String[] args) {
        TabelaHash<String, Integer> tabela = new TabelaHash<>();
        tabela.inserir("chave1", 42);
        tabela.inserir("chave2", 17);
        tabela.inserir("chave3", 99);

        System.out.println("Valor da chave1: " + tabela.buscar("chave1"));
        System.out.println("Valor da chave2: " + tabela.buscar("chave2"));
        System.out.println("Valor da chave3: " + tabela.buscar("chave3"));

        tabela.remover("chave2");
        System.out.println("Valor da chave2 após remoção: " + tabela.buscar("chave2"));
    }
}