# NothingCraft Launcher

**Minecraft Java Edition para Android — versão comunitária não oficial.**

O NothingCraft agora usa o motor real de inicialização do [ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2), que por sua vez usa tecnologia de execução do [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher). A interface principal, gerenciador de versões, runtimes Java, downloads e fluxo de lançamento vêm do projeto upstream; o build aplica a identidade NothingCraft e uma faixa visível de versão modificada não oficial.

> **Aviso:** versão modificada não oficial. Não é afiliada nem endossada por ZalithLauncher, PojavLauncher, Nothing Technology Ltd. ou Mojang/Microsoft.

## Versão 1.0.2

- Substitui o protótipo de interface sem runtime por uma base real de launcher Java para Android.
- Gerencia versões do Minecraft Java e seus componentes pelo fluxo do upstream.
- Mantém o pacote Android `com.f1nozenix.nothingcraft`.
- Compilação otimizada para **arm64**, adequada ao Nothing Phone (1) e ao Snapdragon 778G+.
- APK publicado automaticamente em Releases pelo GitHub Actions.

## Baixar

Quando a compilação terminar, o APK estará em [Releases](https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases) com o nome **NothingCraft Launcher 1.0.2.apk**. Se a execução falhar, confira [Actions](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions).

## Compatibilidade e limites

- Android 8.0 ou superior.
- O artefato desta pipeline é arm64; não é destinado a dispositivos x86.
- O Minecraft Java ainda requer arquivos de jogo legítimos e login/conta conforme as regras da Mojang/Microsoft. O launcher não inclui arquivos proprietários do Minecraft.
- O renderer, a compatibilidade de versões, mods e desempenho variam por versão do jogo e pelo dispositivo.
- **FSR e geração de quadros não são prometidos por esta versão**; só estarão disponíveis se o renderer/modo gráfico selecionado suportar esses recursos.
- A primeira execução pode baixar runtimes e componentes e exigir espaço livre e conexão estável.

## Fonte upstream e reprodutibilidade

A compilação usa o repositório público do ZalithLauncher2 no commit exato registrado em [`scripts/UPSTREAM_COMMIT.txt`](scripts/UPSTREAM_COMMIT.txt), executa as modificações reproduzíveis em [`scripts/patch_upstream.py`](scripts/patch_upstream.py) e então compila com [`scripts/build-nothingcraft.sh`](scripts/build-nothingcraft.sh). Esses scripts são parte do código-fonte correspondente à distribuição. O projeto upstream e os patches permanecem acessíveis publicamente.

- [ZalithLauncher2 — fonte upstream](https://github.com/ZalithLauncher/ZalithLauncher2)
- [PojavLauncher — projeto de engine relacionado](https://github.com/PojavLauncherTeam/PojavLauncher)
- [Licença GPL-3.0](LICENSE)
- [Créditos e avisos de terceiros](THIRD_PARTY_NOTICES.md)

## Assinatura e atualização

O build comunitário usa a chave de depuração pública incluída no upstream para permitir builds reproduzíveis; **não é uma chave privada de distribuição segura**. O pacote e o nome de versão são estáveis, mas quem distribuir versões públicas deve configurar uma chave própria privada e mantê-la fora do repositório. Se o Android rejeitar uma atualização de uma instalação anterior do protótipo, desinstale a versão antiga uma vez e instale o APK 1.0.2. Isso pode apagar os dados locais do protótipo.

## Créditos

Código principal e runtime de launcher: **MovTery e contribuidores do ZalithLauncher2**, sob GPL-3.0. O ZalithLauncher2 usa tecnologia de execução derivada/relacionada ao PojavLauncher e inclui bibliotecas de terceiros listadas no README upstream. Os avisos de copyright e as licenças upstream devem ser mantidos em cópias modificadas.

NothingCraft é uma versão comunitária modificada, com nome distinto, e não é um fork oficial nem possui endosso dos projetos mencionados.
