package com.f1nozenix.nothingcraft

import android.app.Activity
import android.os.Bundle
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.rgb(8, 8, 8)
    private val panel = Color.rgb(20, 20, 20)
    private val panel2 = Color.rgb(29, 29, 29)
    private val fg = Color.rgb(248, 248, 248)
    private val muted = Color.rgb(151, 151, 151)
    private val line = Color.rgb(47, 47, 47)
    private val prefs by lazy { getSharedPreferences("nothingcraft_settings", MODE_PRIVATE) }
    private var scale = 75
    private var memory = 3072
    private var tab = "home"
    private var profile = "1.21.1"
    private lateinit var root: LinearLayout
    private lateinit var body: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        scale = prefs.getInt("render_scale", 75)
        memory = prefs.getInt("memory_mb", 3072)
        profile = prefs.getString("profile", "1.21.1") ?: "1.21.1"
        render()
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun shape(color: Int, radius: Int = 20, stroke: Int = 0) = GradientDrawable().apply {
        setColor(color); cornerRadius = dp(radius).toFloat()
        if (stroke != 0) setStroke(dp(1), stroke)
    }
    private fun text(s: String, size: Float, color: Int = fg, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = s; textSize = size; setTextColor(color)
            if (bold) typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setLineSpacing(dp(2).toFloat(), 1f)
        }
    private fun space(height: Int) = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, dp(height)) }
    private fun sectionTitle(s: String) = text(s.uppercase(), 11f, muted, true).apply {
        letterSpacing = 0.12f
        setPadding(0, dp(4), 0, dp(12))
    }
    private fun card(padding: Int = 18, color: Int = panel) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(padding), dp(padding), dp(padding), dp(padding))
        background = shape(color, 22, line)
    }
    private fun actionButton(s: String, primary: Boolean = false, action: () -> Unit) =
        TextView(this).apply {
            text = s; textSize = 14f; typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER; setTextColor(if (primary) Color.BLACK else fg)
            background = shape(if (primary) fg else panel2, 16, if (primary) 0 else line)
            isClickable = true; isFocusable = true
            setPadding(dp(12), dp(15), dp(12), dp(15))
            setOnClickListener { action() }
        }
    private fun add(parent: LinearLayout, view: View) {
        val requestedHeight = view.layoutParams?.height ?: -2
        parent.addView(view, LinearLayout.LayoutParams(-1, if (requestedHeight > 0) requestedHeight else -2))
    }
    private fun row(vararg views: View) = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
        views.forEachIndexed { i, v ->
            val lp = LinearLayout.LayoutParams(0, -2, 1f)
            if (i > 0) lp.leftMargin = dp(8)
            addView(v, lp)
        }
    }
    private fun pill(s: String, color: Int = muted) = text("•  $s", 10f, color, true).apply {
        background = shape(panel2, 50)
        setPadding(dp(10), dp(7), dp(10), dp(7))
    }

    private fun render() {
        root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(bg) }
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(22), dp(14), dp(22), dp(14))
        }
        header.addView(DotMark(this), LinearLayout.LayoutParams(dp(34), dp(34)))
        val brand = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(10), 0, 0, 0) }
        brand.addView(text("NOTHINGCRAFT", 13f, fg, true))
        brand.addView(text("JAVA LAUNCHER", 9f, muted, true))
        header.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))
        header.addView(text("1.0.1", 11f, muted, true).apply {
            background = shape(panel, 12, line); setPadding(dp(10), dp(7), dp(10), dp(7))
        })
        add(root, header)

        val scroll = ScrollView(this).apply { clipToPadding = false; isFillViewport = true }
        body = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(12), dp(20), dp(26))
        }
        scroll.addView(body)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        when (tab) {
            "home" -> home()
            "versions" -> versions()
            else -> settings()
        }
        add(root, navigation())
        setContentView(root)
    }

    private fun home() {
        add(body, text("SEU MUNDO.\nSUAS REGRAS.", 30f, fg, true))
        add(body, space(10))
        add(body, text("Minecraft Java no Android, com uma interface feita para você.", 14f, muted))
        add(body, space(20))

        val hero = card(20, Color.rgb(17, 17, 17))
        hero.addView(row(text("PERFIL ATUAL", 10f, muted, true), pill("PRÉ-CONFIGURAÇÃO", Color.rgb(205, 205, 205))))
        hero.addView(space(18))
        hero.addView(text(profile, 32f, fg, true))
        hero.addView(text("Minecraft: Java Edition", 13f, muted))
        hero.addView(space(18))
        hero.addView(View(this).apply { setBackgroundColor(line); layoutParams = LinearLayout.LayoutParams(-1, dp(1)) })
        hero.addView(space(14))
        hero.addView(row(stat("RENDERIZAÇÃO", "$scale%"), stat("MEMÓRIA", "${memory / 1024} GB")))
        hero.addView(space(18))
        hero.addView(actionButton("▶     INICIAR JOGO", true) {
            Toast.makeText(this, "O runtime do Minecraft Java ainda precisa ser integrado.", Toast.LENGTH_LONG).show()
        })
        hero.addView(space(8))
        hero.addView(text("O jogo ainda não pode ser iniciado nesta versão.", 11f, muted))
        add(body, hero)
        add(body, space(24))
        add(body, sectionTitle("Acesso rápido"))
        add(body, row(
            quickCard("▦", "Perfis", "Versões Java") { tab = "versions"; render() },
            quickCard("◉", "Desempenho", "Memória e escala") { tab = "settings"; render() }
        ))
        add(body, space(24))
        add(body, sectionTitle("Estado do projeto"))
        val status = card(16)
        status.addView(statusRow("Interface", "ATIVA", fg))
        status.addView(space(12))
        status.addView(statusRow("Runtime Java", "EM DESENVOLVIMENTO", Color.rgb(220, 190, 120)))
        status.addView(space(12))
        status.addView(statusRow("FSR / frame generation", "NÃO IMPLEMENTADO", muted))
        add(body, status)
        add(body, space(18))
        add(body, text("Inspirado na experiência de launchers Java para Android. Não afiliado ao ZalithLauncher, Nothing ou Mojang.", 10f, muted))
    }

    private fun stat(title: String, value: String) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        addView(text(title, 9f, muted, true))
        addView(space(4))
        addView(text(value, 18f, fg, true))
    }

    private fun quickCard(symbol: String, title: String, subtitle: String, action: () -> Unit) =
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = shape(panel, 18, line)
            setPadding(dp(15), dp(16), dp(12), dp(16))
            isClickable = true; isFocusable = true
            addView(text(symbol, 22f, fg, true))
            addView(space(12))
            addView(text(title, 14f, fg, true))
            addView(text(subtitle, 11f, muted))
            setOnClickListener { action() }
        }

    private fun statusRow(label: String, value: String, color: Int) = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
        addView(text(label, 13f, fg), LinearLayout.LayoutParams(0, -2, 1f))
        addView(text(value, 9f, color, true))
    }

    private fun versions() {
        add(body, text("Perfis", 30f, fg, true))
        add(body, space(8))
        add(body, text("Escolha qual versão deseja preparar. A seleção fica salva neste aparelho.", 14f, muted))
        add(body, space(22))
        add(body, sectionTitle("Versões de referência"))
        listOf("1.21.1", "1.20.1", "1.19.4", "1.18.2").forEach { version ->
            val selected = profile == version
            val item = card(16, if (selected) Color.rgb(31, 31, 31) else panel)
            item.orientation = LinearLayout.HORIZONTAL
            item.gravity = Gravity.CENTER_VERTICAL
            val info = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
            info.addView(text("Minecraft Java $version", 15f, fg, true))
            info.addView(text(if (selected) "Perfil selecionado" else "Perfil de referência · não instalado", 11f, muted))
            item.addView(info, LinearLayout.LayoutParams(0, -2, 1f))
            item.addView(text(if (selected) "✓" else "＋", 22f, if (selected) fg else muted, true))
            item.isClickable = true; item.isFocusable = true
            item.setOnClickListener {
                profile = version
                prefs.edit().putString("profile", profile).apply()
                render()
            }
            add(body, item)
            add(body, space(10))
        }
        add(body, space(10))
        add(body, text("Essas opções são apenas perfis de interface. Baixar bibliotecas, autenticar e executar o jogo ainda não está implementado.", 12f, muted))
    }

    private fun settings() {
        add(body, text("Desempenho", 30f, fg, true))
        add(body, space(8))
        add(body, text("Ajuste as preferências locais para o perfil selecionado.", 14f, muted))
        add(body, space(22))
        val renderCard = card()
        renderCard.addView(text("Escala de renderização", 16f, fg, true))
        renderCard.addView(space(5))
        renderCard.addView(text("Preferência salva: será aplicada quando existir um renderer integrado.", 12f, muted))
        renderCard.addView(space(16))
        renderCard.addView(row(
            actionButton("− 10%", false) { scale = (scale - 10).coerceAtLeast(30); savePrefs() },
            text("$scale%", 24f, fg, true).apply { gravity = Gravity.CENTER },
            actionButton("+ 10%", false) { scale = (scale + 10).coerceAtMost(100); savePrefs() }
        ))
        add(body, renderCard)
        add(body, space(14))
        val memoryCard = card()
        memoryCard.addView(text("Memória máxima", 16f, fg, true))
        memoryCard.addView(space(5))
        memoryCard.addView(text("Valor de referência para uma futura configuração da JVM.", 12f, muted))
        memoryCard.addView(space(16))
        memoryCard.addView(row(
            actionButton("− 512 MB", false) { memory = (memory - 512).coerceAtLeast(1024); savePrefs() },
            text("$memory MB", 18f, fg, true).apply { gravity = Gravity.CENTER },
            actionButton("+ 512 MB", false) { memory = (memory + 512).coerceAtMost(6144); savePrefs() }
        ))
        add(body, memoryCard)
        add(body, space(14))
        val about = card()
        about.addView(sectionTitle("Sobre"))
        about.addView(statusRow("Versão do launcher", "1.0.1", fg))
        about.addView(space(12))
        about.addView(statusRow("Sistema-alvo", "ANDROID 8+", fg))
        about.addView(space(12))
        about.addView(statusRow("Dados", "LOCAIS", fg))
        about.addView(space(14))
        about.addView(text("Interface inspirada no ZalithLauncher2 · GPL-3.0", 10f, muted))
        add(body, about)
    }

    private fun savePrefs() {
        prefs.edit().putInt("render_scale", scale).putInt("memory_mb", memory).apply()
        render()
    }

    private fun navigation() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        setPadding(dp(10), dp(9), dp(10), dp(12))
        setBackgroundColor(Color.rgb(12, 12, 12))
        listOf(Triple("home", "⌂", "Início"), Triple("versions", "▦", "Perfis"), Triple("settings", "⚙", "Ajustes")).forEach { item ->
            val active = tab == item.first
            val button = LinearLayout(this@MainActivity).apply {
                orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER
                background = if (active) shape(Color.rgb(36, 36, 36), 15) else null
                setPadding(0, dp(9), 0, dp(8))
                addView(text(item.second, 20f, if (active) fg else muted, true).apply { gravity = Gravity.CENTER })
                addView(text(item.third, 10f, if (active) fg else muted, active).apply { gravity = Gravity.CENTER })
                isClickable = true; isFocusable = true
                setOnClickListener { tab = item.first; render() }
            }
            addView(button, LinearLayout.LayoutParams(0, -2, 1f).apply {
                if (item.first != "home") leftMargin = dp(5)
            })
        }
    }

    private class DotMark(context: MainActivity) : View(context) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val stepX = width / 4f
            val stepY = height / 4f
            val radius = width.coerceAtMost(height) / 13f
            for (r in 0..2) for (c in 0..2) {
                if ((r == 1 && c == 1) || (r == 0 && c == 2)) continue
                canvas.drawCircle(stepX * (c + 1), stepY * (r + 1), radius, paint)
            }
        }
    }
}
