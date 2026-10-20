# NothingCraft Launcher

**Minecraft Java no Android. Do seu jeito.**

Launcher Android em desenvolvimento, com visual AMOLED monocromático e interface inspirada em launchers Java para Android.

[![Android CI](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions/workflows/android.yml/badge.svg)](https://github.com/F1N0ZENIX/NothingCraft-Launcher/actions/workflows/android.yml)

## Versão 1.0.1

- Interface redesenhada com navegação inferior, cartões de perfil, páginas de perfis e ajustes.
- Preferências locais para perfil, escala e memória de referência.
- Pipeline para compilar e publicar automaticamente um GitHub Release em cada atualização versionada.
- APK de release nomeado `NothingCraft Launcher 1.0.1.apk`.

## Estado real dos recursos

Esta versão melhora a interface e o pipeline de distribuição. **Ainda não inicia o Minecraft Java**: o runtime, autenticação, download de bibliotecas e gerenciamento real de versões precisam ser integrados. Os perfis mostrados são referências visuais, não versões instaladas. Escala e memória são preferências guardadas localmente e ainda não alteram o jogo. FSR e frame generation não estão implementados.

## Alvo

- Dispositivo de referência: Nothing Phone (1), Snapdragon 778G+.
- Android 8.0 ou superior.
- Kotlin / Android nativo.
- Estética AMOLED, preto e branco.

## Releases automáticos

O workflow `.github/workflows/android.yml` compila o APK e cria o Release correspondente ao `versionName` de `app/build.gradle` em cada push para `main`. A versão desta entrega é `1.0.1` (`versionCode 2`).

O APK será publicado como `NothingCraft Launcher XX.XX.XX.apk`. Antes de cada nova atualização, aumente `versionCode` e `versionName`; cada versão deve ter uma tag exclusiva.

## Instalar por cima da versão anterior

O aplicativo mantém o mesmo `applicationId`: `com.f1nozenix.nothingcraft`. Para o Android permitir atualização sem desinstalar, a assinatura do APK novo também precisa ser a mesma da instalação atual. O workflow aceita uma chave privada estável nos GitHub Actions secrets (`ANDROID_KEYSTORE_BASE64`, `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD`). Sem esses secrets, gera um APK assinado com a chave de depuração do runner, útil para teste, mas **não há garantia de atualização direta entre execuções**. Configure uma chave privada estável antes de distribuir atualizações contínuas.

## Créditos

A organização visual desta interface foi inspirada no [ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2), projeto de launcher Minecraft Java para Android publicado sob a licença GPL-3.0. Esta versão usa uma implementação de interface própria e não copia os arquivos-fonte do ZalithLauncher2; não é uma fork direta do seu código. Créditos aos desenvolvedores e contribuidores do ZalithLauncher2 pela referência de design. NothingCraft não é afiliado à ZalithLauncher, Nothing Technology Ltd. ou Mojang/Microsoft.

## Privacidade

As preferências ficam localmente no aparelho. Não há sincronização de conta nem telemetria própria implementada.

## Desenvolvimento local

Requer JDK 17 e Android SDK 35. Execute `gradle assembleDebug` ou `./gradlew assembleDebug` caso o wrapper esteja presente.

**NOTHINGCRAFT · Less noise. More blocks.**
