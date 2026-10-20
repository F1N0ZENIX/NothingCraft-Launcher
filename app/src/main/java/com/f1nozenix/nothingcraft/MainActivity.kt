package com.f1nozenix.nothingcraft

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.BLACK
    private val fg = Color.rgb(245, 245, 245)
    private val muted = Color.rgb(155, 155, 155)
    private val prefs by lazy { getSharedPreferences("nothingcraft_settings", MODE_PRIVATE) }
    private var scale = 75
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        scale = prefs.getInt("render_scale", 75)
        render()
    }
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun label(s: String, size: Float, color: Int = fg, bold: Boolean = false) = TextView(this).apply {
        text = s; textSize = size; setTextColor(color); if (bold) typeface = Typeface.DEFAULT_BOLD
        setPadding(0, dp(4), 0, dp(4))
    }
    private fun button(s: String, action: () -> Unit) = Button(this).apply {
        text = s; setTextColor(fg); setBackgroundColor(Color.rgb(30,30,30)); isAllCaps = false
        setOnClickListener { action() }
    }
    private fun render() {
        val page = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(24),dp(24),dp(24),dp(24)); setBackgroundColor(bg) }
        val scroll = ScrollView(this); scroll.addView(page)
        page.addView(label("N O T H I N G C R A F T", 12f, muted, true))
        page.addView(label("Java Edition.\nNo seu ritmo.", 34f, fg, true).apply { setPadding(0,dp(24),0,dp(12)) })
        page.addView(label("LAUNCHER  /  ANDROID", 11f, muted, true))
        page.addView(label("A base da sua experiência Minecraft Java no celular.", 15f).apply { setPadding(0,dp(12),0,dp(24)) })
        page.addView(label("01  /  JOGAR", 12f, muted, true))
        page.addView(button("Preparar sessão  →") { Toast.makeText(this, "O runtime Java ainda será integrado nesta versão inicial.", Toast.LENGTH_LONG).show() }, LinearLayout.LayoutParams(-1,dp(54)).apply { topMargin=dp(8); bottomMargin=dp(24) })
        page.addView(label("02  /  DESEMPENHO", 12f, muted, true))
        page.addView(label("Escala de renderização: $scale%", 17f, fg, true).apply { setPadding(0,dp(12),0,dp(4)) })
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row.addView(button("− 10%") { scale=(scale-10).coerceAtLeast(30); save() }, LinearLayout.LayoutParams(0,dp(48),1f).apply { rightMargin=dp(8) })
        row.addView(button("+ 10%") { scale=(scale+10).coerceAtMost(100); save() }, LinearLayout.LayoutParams(0,dp(48),1f))
        page.addView(row)
        page.addView(label("Preferência salva localmente. A escala só será aplicada ao jogo após integrar um renderer compatível.",12f,muted).apply { setPadding(0,dp(8),0,dp(24)) })
        page.addView(label("03  /  STATUS DO PROJETO",12f,muted,true))
        page.addView(label("INTERFACE  •  PRONTA\nRUNTIME JAVA  •  PENDENTE\nFSR / FRAME GENERATION  •  NÃO IMPLEMENTADOS",12f).apply { setPadding(0,dp(12),0,dp(24)) })
        page.addView(label("NOTHINGCRAFT  ·  RELEASE 1.0.0",10f,muted,true))
        setContentView(scroll)
    }
    private fun save() { prefs.edit().putInt("render_scale",scale).apply(); render() }
}
