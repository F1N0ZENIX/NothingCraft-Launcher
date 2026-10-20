# NothingCraft Launcher

**Minecraft Java no Android. Do seu jeito.**

Uma experiência Android inspirada na estética minimalista Nothing OS: preto AMOLED, branco, matriz de pontos e foco em desempenho.

[![Android CI](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions/workflows/android.yml/badge.svg)](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions/workflows/android.yml)

## Estado da versão inicial

Esta versão estabelece a base Android, identidade visual, preferências locais e pipeline de compilação. A execução real do Minecraft Java ainda depende da integração com runtime e componentes compatíveis. FSR e frame generation não estão implementados nesta versão e não devem ser anunciados como funcionais.

## Objetivos

- Interface preta AMOLED com detalhes monocromáticos pontilhados.
- Avaliar integração com [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher), respeitando licenças e dependências.
- Preparar controles de resolução e escala de renderização.
- Desenvolver pensando no Nothing Phone (1), Snapdragon 778G+.
- Compilar APK automaticamente com GitHub Actions e anexá-lo aos Releases por tag.
- Preservar dados nas atualizações mantendo application ID e assinatura.

## Compatibilidade-alvo

| Item | Alvo |
|---|---|
| Dispositivo | Nothing Phone (1) |
| Processador | Snapdragon 778G+ |
| Plataforma | Android 8.0+ |
| Linguagem | Kotlin |
| Interface | AMOLED, branco e pontos |

## Gráficos, FSR e desempenho

FSR não é um recurso universal do Android: precisa ser integrado ao caminho de renderização escolhido. O resultado depende do renderer, drivers, versão do Minecraft e mods. Frame generation também exige implementação compatível e testes reais.

## Instalar

1. Abra [Releases](https://github.com/F1N0ZENIX/NothingCraft-Launcher/releases).
2. Baixe o APK anexado à versão desejada.
3. Autorize a instalação se o Android solicitar.
4. Instale sobre a versão anterior. Não desinstale se quiser preservar dados.

## Compilar localmente

Requer JDK 17 e Android SDK.

```bash
git clone https://github.com/F1N0ZENIX/NothingCraft-Launcher.git
cd NothingCraft-Launcher
./gradlew assembleDebug
```

## Releases automáticos

O workflow `.github/workflows/android.yml` compila o APK em pushes e cria um GitHub Release ao receber uma tag como `v1.0.0`. O arquivo será chamado `NothingCraft-Launcher-v1.0.0.apk`.

```bash
git tag v1.0.0
git push origin v1.0.0
```

O Android só permite atualização direta quando application ID e assinatura são os mesmos e a versão nova tem versionCode superior. Para distribuição contínua, configure uma chave de assinatura própria nos secrets do GitHub e mantenha-a protegida. O pipeline inicial gera APK release sem assinatura própria; não publique atualizações públicas contínuas sem resolver a assinatura.

## Privacidade

As preferências desta base ficam no dispositivo. Não há sincronização de conta nem telemetria própria implementada.

## Licenças

Projeto independente, sem afiliação à Nothing Technology Ltd. ou Mojang/Microsoft. Minecraft é marca de seus proprietários. Nenhum arquivo proprietário do jogo é distribuído. Código e dependências de terceiros devem manter suas respectivas licenças.

## Roadmap

- [x] Base Android e identidade visual AMOLED.
- [x] Preferências locais iniciais.
- [x] CI de compilação e release por tag.
- [ ] Integrar runtime Java e autenticação compatíveis.
- [ ] Instalação/seleção de versões e gestão de bibliotecas.
- [ ] Validar escala de resolução no aparelho físico.
- [ ] Investigar integração real de FSR no renderer escolhido.
- [ ] Testar atualização assinada entre releases.

**NOTHINGCRAFT · Less noise. More blocks.**

## APKs e versões

Os APKs gerados pelo GitHub Actions usam o nome `NothingCraft Launcher XX.XX.XX.apk`, em que `XX.XX.XX` é o valor de `versionName` em `app/build.gradle` (por exemplo, `NothingCraft Launcher 1.0.0.apk`). Builds de teste ficam nos artefatos da execução; releases versionadas ficam na página Releases.
