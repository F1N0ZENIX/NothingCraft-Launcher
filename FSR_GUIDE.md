# AMD FidelityFX FSR 1 + Minecraft Java 26.3

Este guia é para o mod **NothingCraft Snapdragon Optimizer**, distribuído separadamente do APK. Ele usa a implementação FSR 1 (EASU + RCAS) incluída no projeto open-source Afterburner Optimized.

## Instalar

1. Instale Minecraft Java **26.3**, Fabric Loader **0.19.5 ou superior** e Java **25**.
2. Baixe `NothingCraft-Snapdragon-Optimizer-1.0.1.jar` na [release do NothingCraft](https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases).
3. Copie o JAR para a pasta `mods` da instância 26.3.
4. Abra o jogo primeiro sem Sodium, Iris ou outros mods de renderização, para verificar que a instância inicia normalmente.

## Ativar FSR 1

A implementação FSR 1 faz parte do caminho experimental de renderização do mod. Ela **não é geração de quadros**:

- **EASU** amplia a imagem renderizada em resolução interna menor.
- **RCAS** aplica nitidez depois do upscaling.

Para ativar, abra as opções do Afterburner/Shader settings dentro do jogo e alterne o modo de upscaling para `Ultra Quality`, `Quality`, `Balanced` ou `Performance`. Se não encontrar o controle na interface, feche o jogo e crie/edite o arquivo:

`config/afterburner-shaders.properties`

Coloque uma destas linhas:
- `upscale=ultra_quality` — menor redução de resolução, boa primeira tentativa.
- `upscale=quality` — equilíbrio entre nitidez e desempenho.
- `upscale=balanced` — renderiza mais baixo; pode ficar mais borrado.
- `upscale=performance` — maior redução; qualidade visual menor.
- `upscale=off` — desativa o FSR.

Se o arquivo já existir, mantenha as demais configurações e altere apenas a propriedade `upscale`.

## Perfil inicial para Snapdragon 778G+ / Nothing Phone (1)

Comece de forma conservadora:
- Distância de renderização: **6–8 chunks**.
- Distância de simulação: **4–6 chunks**.
- Gráficos: **Rápidos**.
- Partículas: **Reduzidas**.
- Nuvens: **Desligadas**.
- Limite de FPS: **45 ou 60**, escolhendo o mais estável e frio.
- FSR: teste primeiro `ultra_quality`, depois `quality`.

Compare o mesmo mundo e a mesma posição com e sem FSR. O ganho depende de a GPU estar limitada pela resolução; se o gargalo for CPU, chunk generation, memória ou o próprio renderer, a melhora pode ser pequena ou inexistente.

## Compatibilidade e recuperação

- O mod é destinado a Minecraft Java 26.3/Fabric, mas o suporte ao renderer específico do Android não é garantido.
- O FSR precisa que o renderer exponha as APIs gráficas exigidas pelos shaders. O pipeline experimental pode não funcionar em alguns renderizadores móveis.
- Sodium/Iris e outros mods de renderização podem desativar partes do pipeline do Afterburner para evitar conflitos.
- Se aparecer tela preta, artefatos, falha de compilação de shader ou crash, defina `upscale=off` ou remova o mod e teste a instância sem mods.
- Faça backup dos mundos e teste em uma instância separada.

## Créditos

- [Afterburner Optimized](https://github.com/HamdanProfessional/afterburner-optimized) — base do mod, licença MIT.
- [AMD FidelityFX Super Resolution](https://gpuopen.com/fidelityfx-superresolution/) — algoritmo publicado que inspira a implementação FSR 1 do upstream.
- [Notas oficiais de Minecraft Java 26.3](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-3).
