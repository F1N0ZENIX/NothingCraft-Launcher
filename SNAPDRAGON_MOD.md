# NothingCraft Snapdragon Optimizer

Mod Fabric comunitário para **Minecraft Java 26.3**, focado em otimizações de jogo/renderização em dispositivos móveis e com o caminho de upscaling AMD FidelityFX Super Resolution 1 (FSR 1).

## O que contém

- Base open-source Afterburner Optimized, com otimizações de renderização, memória, iluminação e lógica do jogo.
- Detecção de ambiente Android usada pelo upstream para começar com opções móveis conservadoras.
- Upscaling espacial FSR 1 (EASU + RCAS) integrado ao pipeline experimental do upstream.
- Metadados reidentificados como NothingCraft, mantendo o ID técnico interno e compatibilidade de mixins upstream.
- Licença MIT e avisos do autor original preservados no JAR.

## Instalação

1. Use Minecraft Java 26.3 com Fabric Loader 0.19.5+ e Java 25.
2. Copie `NothingCraft-Snapdragon-Optimizer-1.0.0.jar` para `mods` da instância.
3. Teste sem Sodium/Iris/outros mods de renderização primeiro.
4. Para ativar FSR 1, alterne o modo de upscaling no painel Afterburner/Shader settings do jogo. Se o controle não estiver disponível, edite `config/afterburner-shaders.properties` e use `upscale=quality`.
5. Se o jogo não iniciar, defina `upscale=off` ou remova o mod e confirme que a instância base 26.3 funciona.

## Perfil inicial para Snapdragon 778G+

Comece com 6–8 chunks de renderização, 4–6 de simulação, partículas reduzidas, nuvens desligadas e limite de 45–60 FPS. O FSR 1 reduz a resolução interna e reconstrói a imagem, mas não gera quadros. Compare FPS, estabilidade e temperatura no mesmo mundo, com as mesmas configurações.

## Limitações

- Compatibilidade com Minecraft 26.3 e Fabric não prova que o FSR funcione em cada renderer Android.
- O FSR 1 depende de suporte a shaders e recursos gráficos exigidos pelo pipeline.
- Não garantimos um aumento específico de FPS, estabilidade térmica ou funcionamento com todos os mods.
- Use backup de mundos e teste em uma instância separada.

## Créditos e licença

Baseado em [Afterburner Optimized](https://github.com/HamdanProfessional/afterburner-optimized), de Hamdan, sob MIT. O aviso de licença do upstream é incluído no JAR. A implementação FSR 1 do upstream é escrita com base no algoritmo publicado pela AMD.
