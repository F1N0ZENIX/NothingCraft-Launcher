# NothingCraft Launcher

**Minecraft Java Edition para Android — versão comunitária não oficial.**

O NothingCraft usa o motor de inicialização do [ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2), relacionado à tecnologia de execução do [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher). Esta versão personaliza a interface para o estilo Nothing OS: AMOLED preto, contraste preto/branco, tema Urban Ash monocromático e assinatura de pontos.

> **Aviso:** versão modificada não oficial. Não é afiliada nem endossada por ZalithLauncher, PojavLauncher, Nothing Technology Ltd. ou Mojang/Microsoft.

## Versão 1.0.3

- Interface em tema monocromático com fundo AMOLED preto em dispositivos novos/instalações novas.
- Assinatura visual de pontos inspirada na linguagem Nothing, mantendo a indicação legal de versão modificada não oficial.
- Pipeline de build para Android arm64.
- Guia de compatibilidade para Minecraft Java 26.3 e mod de super-resolução/FSR.

## Baixar

O APK será publicado em [Releases](https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases) após a compilação no GitHub Actions. Acompanhe o build em [Actions](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions).

## Minecraft Java 26.3

O launcher obtém as versões de jogo do fluxo upstream, mas **a opção aparecer no seletor não garante que o jogo funcione em todos os renderizadores Android**. A versão 26.3 mudou o backend de janela/entrada para SDL3 e usa novas rotinas gráficas; no Android, o resultado depende do renderer, das bibliotecas nativas e do driver. Veja [FSR_GUIDE.md](FSR_GUIDE.md) para o caminho recomendado de teste.

## FSR / upscaling

O NothingCraft não implementa FSR dentro do launcher: o upscaling precisa ser integrado ao renderer ou ao próprio jogo. O projeto [Super Resolution](https://modrinth.com/mod/superresolution) lista suporte ao Minecraft 26.3 e algoritmos FSR 1/2/3, mas declara requisito de OpenGL 4.3 e extensões específicas. **Compatibilidade com Android ARM64/renderer do Nothing Phone (1) não é garantida** e precisa ser testada no dispositivo. O launcher não instala o mod automaticamente nem promete geração de quadros.

Consulte o [guia FSR e 26.3](FSR_GUIDE.md) antes de instalar mods. Faça testes em uma instância separada e mantenha backup dos mundos.

## Compatibilidade e limites

- Android 8.0 ou superior.
- O artefato desta pipeline é arm64; não é destinado a dispositivos x86.
- O Minecraft Java exige arquivos legítimos e login/conta conforme as regras da Mojang/Microsoft; o launcher não inclui arquivos proprietários do jogo.
- Runtimes, mods, renderizadores e versões de Minecraft podem exigir configurações diferentes.
- A primeira execução pode baixar runtimes e componentes e exigir espaço livre e conexão estável.

## Fonte upstream e reprodutibilidade

A compilação usa o repositório público do ZalithLauncher2 no commit exato registrado em [scripts/UPSTREAM_COMMIT.txt](scripts/UPSTREAM_COMMIT.txt), aplica modificações reproduzíveis em [scripts/patch_upstream.py](scripts/patch_upstream.py) e compila por [scripts/build-nothingcraft.sh](scripts/build-nothingcraft.sh). Os patches e os avisos de terceiros permanecem disponíveis no código-fonte.

- [ZalithLauncher2 — fonte upstream](https://github.com/ZalithLauncher/ZalithLauncher2)
- [PojavLauncher — projeto de engine relacionado](https://github.com/PojavLauncherTeam/PojavLauncher)
- [Licença GPL-3.0](LICENSE)
- [Créditos e avisos de terceiros](THIRD_PARTY_NOTICES.md)

## Assinatura e atualização

O build comunitário usa a chave de depuração pública incluída no upstream para builds reproduzíveis; **não é uma chave privada de distribuição segura**. Se o Android rejeitar uma atualização de uma instalação anterior do protótipo, será necessário desinstalar a versão antiga uma vez, o que pode apagar dados locais. As atualizações futuras dependem de manter a mesma chave de assinatura.

## Créditos

Código principal e runtime de launcher: MovTery e contribuidores do ZalithLauncher2, sob GPL-3.0. O ZalithLauncher2 usa tecnologia de execução relacionada ao PojavLauncher e inclui bibliotecas de terceiros listadas no README upstream. Avisos de copyright e licenças devem ser mantidos.

NothingCraft é uma versão comunitária modificada, com nome distinto, e não é um fork oficial nem possui endosso dos projetos mencionados.
