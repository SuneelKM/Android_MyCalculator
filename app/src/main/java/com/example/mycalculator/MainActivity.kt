package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


import javax.script.ScriptEngineManager


class MainActivity : AppCompatActivity() {
    var expression = ""
    var answer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberEntry(view: View) {
        if (resultView.getText().toString() == "0" || answer == 0) {
            expression = ""
            resultView.setText((view as Button).text)
            answer = 1
        } else {
            resultView.append((view as Button).text)
        }
        expression += (view as Button).text
    }

    fun operation(view: View) {

        val input = (view as Button).text
        answer = 1
        val calcText = resultView.getText().toString()
        if (calcText == "0") expression = "0"
        val lastItem = calcText.substring(calcText.length - 1)

        if (input == "AC" || calcText == "Infinity") {
            resultView.setText("0")
            expression = ""
        } else {
            if ((lastItem in "÷+-×") && (input != ".")) {
                resultView.setText(calcText.substring(0, calcText.length - 1))
                expression = "" + expression.substring(0, expression.length - 1)
            }
            if (input == "÷") {
                resultView.append("÷")
                expression += "/"
            } else if (input == "+") {
                resultView.append("+")
                expression += "+"
            } else if (input == "-") {
                resultView.append("-")
                expression += "-"
            } else if (input == "×") {
                resultView.append("×")
                expression += "*"
            } else if (input == "00") {
                if(calcText!="0") {
                    resultView.append("00")
                    expression += "00"
                }
            } else if (input == ".") {
                var l = calcText.length - 1
                if (calcText[l] in "÷×-+") {
                    resultView.append("0.")
                    expression += "0."
                } else {
                    while (l > 0) {
                        if (calcText[l] == '.') {
                            break
                        } else if (calcText[l] in "÷+-×") {
                            resultView.append(".")
                            expression += "."
                            break
                        }
                        l--
                    }
                }
                if (l == 0) {
                    resultView.append(".")
                    expression += "."
                }
            } else if (input == "⌫") {
                if (calcText.length == 1) {
                    resultView.setText("0")
                    expression = ""
                } else {
                    resultView.setText(calcText.substring(0, calcText.length - 1))
                    expression = "" + expression.substring(0, expression.length - 1)
                }
            } else if (input == "=") {
                val mgr = ScriptEngineManager()
                val engine = mgr.getEngineByName("rhino")
                println("Hello1   $expression")

                try {
                    val result = engine.eval(expression).toString().toDouble()
                    println("Hello2   $result")
                    val longResult = result.toLong()
                    if (result == longResult.toDouble())
                        resultView.text = longResult.toString()
                    else
                        resultView.text = result.toFloat().toString()
                    expression = resultView.getText().toString()
                    answer = 0
                } catch (e: Exception) {
                    Toast.makeText(this, "Error!!", Toast.LENGTH_LONG).show()
                }

            }

        }
    }

}