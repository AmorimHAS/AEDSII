#include <stdio.h>

int main() {
    int N, M;
    while (1) {
        char input[10];
        if (scanf("%s", input) == EOF) break;
        if (input[0] == 'F') break;

        N = input[0] - '0';
        scanf("%d", &M);

        int tabuleiro[101][101];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                scanf("%d", &tabuleiro[i][j]);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tabuleiro[i][j] == 1)
                    printf("9");
                else {
                    int count = 0;
                    for (int x = -1; x <= 1; x++)
                        for (int y = -1; y <= 1; y++) {
                            int ni = i + x, nj = j + y;
                            if (ni >= 0 && nj >= 0 && ni < N && nj < M && tabuleiro[ni][nj] == 1)
                                count++;
                        }
                    printf("%d", count);
                }
            }
            printf("\n");
        }
    }
    return 0;
}
