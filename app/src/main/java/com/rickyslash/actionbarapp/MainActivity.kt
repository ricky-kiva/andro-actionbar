package com.rickyslash.actionbarapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.rickyslash.actionbarapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // make binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // make menu displayed on this Activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        // 'getSystemService' returns the 'service object' (SEARCH SERVICE) which need to be typecasted to 'SearchManager'
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        // this will get the 'search' from 'menu XML' file
        // '.actionView' passed because this view got 'actionViewClass' property in the 'XML'
        // 'as SearchView' will typecast 'actionView' as 'SearchView'
        val searchView = menu?.findItem(R.id.search)?.actionView  as SearchView

        // '.getSearchableInfo' will get 'configuration for search interface'
        // '.setSearchableInfo' will set the 'object' (from getSearchableInfo()) to 'searchView'
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // after the 'search interface configuration' 'has been set', we could set 'queryHint'
        // 'queryHint' is the 'text displayed' in 'collapsed search view'
        searchView.queryHint = resources.getString(R.string.search_hint)
        // 'setOnQueryTextListener' will set the 'function' when 'query' 'passed'
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // this 'function run' when user 'submitted' the 'query'
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }

            // this tells it will 'react nothing' when the 'text change'
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    // organize menu items action when clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MenuFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}