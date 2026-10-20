# ◉ NothingCraft

<p align="center">
  <strong>Um launcher de Minecraft Java para Android, com identidade Nothing OS.</strong><br>
  Preto AMOLED · Interface monocromática · FSR 1 · Perfil de desempenho Snapdragon
</p>

<p align="center">
  <a href="https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases"><img alt="Releases" src="https://img.shields.io/github/v/release/F1N0ZENIX/NothingCraft-Launcher?style=for-the-badge&color=000000"></a>
  <a href="https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions"><img alt="Build" src="https://img.shields.io/github/actions/workflow/status/F1N0ZENIX/NothingCraft-Launcher/android.yml?style=for-the-badge&label=build"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/launcher-GPL--3.0-white?style=for-the-badge&labelColor=000000"></a>
</p>

> **Projeto comunitário não oficial.** Não é afiliado nem endossado pela Nothing Technology, Mojang/Microsoft, ZalithLauncher, PojavLauncher ou pelos autores dos mods citados.

## ✦ O projeto

NothingCraft adapta o motor de inicialização do [ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2) para uma experiência visual inspirada no Nothing OS: preto AMOLED, elementos monocromáticos, alto contraste e ícone com assinatura de pontos.

| Recurso | Estado |
|---|---|
| Launcher Minecraft Java para Android ARM64 | Build automatizado |
| Tema AMOLED preto e texto de alto contraste | Incluído na 1.0.4 |
| Ícone adaptativo NothingCraft | Incluído na 1.0.4 |
| Minecraft Java 26.3 | Depende do runtime e renderizador compatíveis |
| Mod Fabric com FSR 1 e otimizações para celulares | Artefato separado; teste no dispositivo |
| FSR funcionando em todo renderer Android | **Não garantido** |

## ↓ Downloads

**Use os arquivos da seção [Releases](https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases).** A release publica o APK e o mod Fabric depois que ambos passam pela compilação.

- **NothingCraft Launcher** — aplicativo Android (ARM64).
- **NothingCraft Snapdragon Optimizer** — mod Fabric para Minecraft Java 26.3; instale o JAR na pasta `mods` da instância compatível.
- [Ver compilações e logs](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions)

Se a release ainda não mostrar os dois arquivos, a compilação não terminou ou falhou. Não use um APK antigo como se fosse a versão mais recente.

## ◉ Interface Nothing OS

- Fundo de tela principal e superfícies base em **preto AMOLED (#000000)**.
- Superfícies elevadas quase pretas para separar cartões sem clarear o fundo inteiro.
- Texto preto em botões brancos e texto branco em superfícies escuras, corrigindo o contraste.
- Ícone adaptativo preto e branco com pontos inspirados na identidade visual Nothing.
- Aviso de versão modificada não oficial preservado na tela principal.

## ⚡ Snapdragon Optimizer + AMD FSR 1

O mod Fabric é construído a partir do projeto open-source [Afterburner Optimized](https://github.com/HamdanProfessional/afterburner-optimized), licenciado sob MIT, com identificação NothingCraft e os recursos upstream para Minecraft Java **26.3**. O código upstream já detecta launchers Android e ajusta algumas opções para celulares; inclui otimizações de renderização, memória e lógica de jogo, além do caminho de upscaling **AMD FidelityFX Super Resolution 1 (FSR 1)** com EASU + RCAS.

### Instalação do mod

1. Instale a versão de Minecraft Java **26.3** e o Fabric Loader exigido pelo mod.
2. Baixe o arquivo `NothingCraft-Snapdragon-Optimizer-1.0.0.jar` da [release](https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases).
3. Coloque o JAR na pasta `mods` da instância 26.3.
4. Inicie primeiro sem outros mods de renderização para testar.
5. Abra as opções do mod e ative o upscaling FSR 1, se o renderizador permitir. Teste primeiro em um mundo descartável.

**Compatibilidade real:** o mod foi criado para Minecraft Java 26.3, mas isso não significa que todas as funções funcionem em Android. O FSR exige que o renderer exponha os recursos gráficos usados pelos shaders; Sodium/Iris e certos renderizadores podem desativar o caminho experimental. Se houver tela preta, artefatos ou falha ao iniciar, desative o FSR/remova o mod e teste sem ele. Nenhum ganho de FPS é prometido sem benchmark no aparelho.

### Por que FSR 1?

FSR 1 é um upscaler espacial: **EASU** aumenta a imagem a partir de uma resolução interna menor e **RCAS** aplica nitidez. Não é geração de quadros nem DLSS. A implementação incluída é baseada no algoritmo publicado pela AMD; consulte os avisos/licença do upstream antes de redistribuir modificações.

## 📱 Perfil para celulares Snapdragon

O mod reconhece ambientes de launchers Android como Pojav/Zalith/FCL e usa padrões móveis mais conservadores em alguns caminhos caros, por exemplo evitando certas rotinas de oclusão que podem custar mais do que economizam em GPUs de celular. Para o Nothing Phone (1), comece com:

- Distância de renderização: 6–8 chunks.
- Distância de simulação: 4–6 chunks.
- Gráficos: Rápidos; nuvens desligadas.
- Partículas: Reduzidas.
- Limite de FPS: 45 ou 60, conforme temperatura e estabilidade.
- FSR 1: comece em Ultra Quality/Quality; reduza mais apenas se o renderer continuar estável.

Esses valores são um ponto de partida, não uma detecção garantida do Snapdragon 778G+. O desempenho depende da versão do jogo, renderizador, temperatura, mods e drivers. Em sessões longas, reduza FPS e distância de renderização se houver aquecimento ou throttling.

## 🧱 Minecraft 26.3

O launcher baixa os metadados e componentes disponíveis no upstream, mas a compatibilidade de uma versão depende também do Java runtime, SDL3, renderer e bibliotecas nativas. Teste 26.3 sem mods antes de instalar o otimizador. Consulte as [notas oficiais da versão](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-3) e o [guia de configuração](FSR_GUIDE.md).

## 🛠️ Compilação reproduzível

- Launcher: fonte [ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2), fixada em `scripts/UPSTREAM_COMMIT.txt`.
- Mod: fonte [Afterburner Optimized](https://github.com/HamdanProfessional/afterburner-optimized), fixada em `scripts/AFTERBURNER_COMMIT.txt).
- Personalização: `scripts/patch_upstream.py`, `scripts/build-nothingcraft.sh` e `scripts/build-snapdragon-mod.sh`.
- CI: GitHub Actions compila o APK e o JAR e publica os artefatos na release.

## 📜 Créditos e licenças

- [ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2) — launcher base, GPL-3.0.
- [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher) — projeto relacionado ao ecossistema de execução de Minecraft Java no Android.
- [Afterburner Optimized](https://github.com/HamdanProfessional/afterburner-optimized) — otimizações e implementação FSR 1, MIT; copyright e licença preservados no artefato.
- AMD FidelityFX FSR 1 — algoritmo publicado pela AMD; consulte a documentação e os termos oficiais antes de redistribuir código derivado.
- [Avisos de terceiros](THIRD_PARTY_NOTICES.md).

Nada neste projeto inclui arquivos proprietários do Minecraft. É necessária uma conta legítima e os arquivos do jogo fornecidos conforme as regras da Mojang/Microsoft.
