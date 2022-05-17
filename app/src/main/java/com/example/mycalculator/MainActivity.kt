package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AndroidRuntimeException
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var expression = ""
    var answer = 0

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
        val last_item = calcText.substring(calcText.length - 1)

        if (input == "AC") {
            resultView.setText("0")
            expression = ""
        } else {
            if (last_item in "÷+-×%.") {
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
            } else if (input == "%") {
                resultView.append("%")
                expression += "*0.01*"
            } else if (input == ".") {
                resultView.append(".")
                expression += "."

            } else if (input == "⌫") {
                if (calcText.length == 1) {
                    resultView.setText("0")
                    expression = ""
                } else {
                    resultView.setText(calcText.substring(0, calcText.length - 1))
                    expression = "" + expression.substring(0, expression.length - 1)
                }
            } else if (input == "=") {
                try {
                    val statement = ExpressionBuilder(expression).build()
                    var result = statement.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) resultView.text = longResult.toString()
                    else {
                        result = Math.round(result*10000000000)/10000000000.0
                        resultView.text = result.toString()
                    }
                    expression = resultView.getText().toString()
                    answer = 0
                } catch (e: ArithmeticException) {
                    Toast.makeText(this, "Can't divide by zero.", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

}