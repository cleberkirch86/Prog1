# Teste de Mesa - Classe `Fracao`

Este teste de mesa demonstra os cálculos realizados pela classe `Fracao` em diferentes operações: soma, subtração, multiplicação e divisão.

## Cenário de Teste
- **Entrada do usuário:**
    - Primeira fração: numerador = `2`, denominador = `5`
    - Segunda fração: numerador = `3`, denominador = `7`
- **Operações realizadas:**
    - Soma, subtração, multiplicação e divisão.

## Tabela de Rastreamento

| **Operação**      | **Fração 1** | **Fração 2** | **Cálculo Realizado**                     | **Resultado Não Simplificado** | **Resultado Simplificado** |
|--------------------|--------------|--------------|-------------------------------------------|---------------------------------|----------------------------|
| **Inicialização**  | 2/5          | 3/7          | -                                         | -                               | 2/5, 3/7                  |
| **Soma**           | 2/5          | 3/7          | (2×7 + 3×5) / (5×7)                       | 29/35                          | 29/35                     |
| **Subtração**      | 2/5          | 3/7          | (2×7 - 3×5) / (5×7)                       | -1/35                          | -1/35                     |
| **Multiplicação**  | 2/5          | 3/7          | (2×3) / (5×7)                             | 6/35                           | 6/35                      |
| **Divisão**        | 2/5          | 3/7          | (2×7) / (5×3)                             | 14/15                          | 14/15                     |

## Explicação
1. **Soma:** A soma é calculada multiplicando cruzadamente os numeradores e denominadores:
    - Numerador: \( (2×7 + 3×5) = 29 \)
    - Denominador: \( (5×7) = 35 \)
    - Resultado: `29/35` (já simplificado).
2. **Subtração:** Segue o princípio da soma, mas subtraindo os produtos cruzados:
    - Numerador: \( (2×7 - 3×5) = -1 \)
    - Denominador: \( (5×7) = 35 \)
    - Resultado: `-1/35` (já simplificado).
3. **Multiplicação:** Os numeradores e denominadores são multiplicados diretamente:
    - Numerador: \( 2×3 = 6 \)
    - Denominador: \( 5×7 = 35 \)
    - Resultado: `6/35` (já simplificado).
4. **Divisão:** A segunda fração é invertida e a multiplicação é realizada:
    - Numerador: \( 2×7 = 14 \)
    - Denominador: \( 5×3 = 15 \)
    - Resultado: `14/15`.

---

Este formato pode ser salvo diretamente em um arquivo `.md`. Se precisar de mais modificações ou ajuda com outro formato, é só avisar! 😊