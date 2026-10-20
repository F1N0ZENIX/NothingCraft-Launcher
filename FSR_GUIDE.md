# Guia de Minecraft Java 26.3 + FSR no NothingCraft

## 1. Minecraft Java 26.3

1. Abra o NothingCraft e entre no gerenciador de versões/downloads.
2. Selecione a versão estável **26.3** e instale-a com os componentes recomendados pelo launcher.
3. Use o runtime Java indicado pelo launcher para essa versão (Minecraft 26.3 exige Java 25).
4. Teste primeiro sem mods, com uma instância nova. Se fechar na tela Mojang, o problema pode ser o renderer/SDL3 e não a lista de versões do launcher.
5. Teste renderizadores disponíveis um por vez. A compatibilidade com 26.3 varia; faça backup dos mundos e não presuma que MobileGlues, GL4ES, Vulkan/Zink ou outros renderizadores sejam intercambiáveis.

A versão oficial 26.3 mudou para SDL3 e atualizou o backend gráfico. Referência: [notas oficiais de Minecraft Java 26.3](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-3).

## 2. FSR / super-resolução

O caminho experimental conhecido para 26.3 é o mod [Super Resolution no Modrinth](https://modrinth.com/mod/superresolution). A página do projeto lista Minecraft 26.3, Fabric/Forge/NeoForge e FSR 1/2/3, mas também exige **OpenGL 4.3+** e extensões como `GL_ARB_direct_state_access`, `GL_ARB_gl_spirv` e `GL_ARB_clear_texture`.

1. Crie uma instância **Fabric 26.3** separada.
2. Baixe somente a build do mod correspondente a Minecraft 26.3 e Fabric na página de versões do projeto. Não use JAR de outra versão do jogo.
3. Instale o Fabric API correspondente caso a versão do mod exija.
4. Coloque o JAR na pasta `mods` da instância e inicie o jogo.
5. Se o mod indicar que o OpenGL/extensões não são suportados ou o jogo fechar, remova o mod e volte à instância sem mods.

### Limitações importantes no Nothing Phone (1)

- O mod anuncia compatibilidade com Java 26.3, mas isso **não comprova suporte ao Android ARM64 nem ao renderer específico usado pelo launcher**.
- FSR 2/3 e geração de quadros dependem de APIs, drivers e caminhos de renderização compatíveis; não são ativados por simplesmente instalar o launcher.
- Não incluí o JAR no APK: ele é um componente de terceiros com builds e dependências próprias. Instale-o apenas a partir da página oficial e valide no dispositivo.
- Se o launcher abrir o jogo mas o mundo ficar invisível, apresentar artefatos ou travar, isso aponta para incompatibilidade gráfica. Teste outro renderer ou aguarde uma versão compatível; não há configuração universal que garanta 26.3 + FSR no Android.

## Fontes

- [Minecraft Java 26.3 — notas oficiais](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-3)
- [Super Resolution — Modrinth](https://modrinth.com/mod/superresolution)
