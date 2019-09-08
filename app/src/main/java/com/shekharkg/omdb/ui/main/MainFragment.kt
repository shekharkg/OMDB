package com.shekharkg.omdb.ui.main

import android.app.SearchManager
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekharkg.omdb.R
import com.shekharkg.omdb.adapter.MoviesAdapter
import com.shekharkg.omdb.dao.Movie
import com.shekharkg.omdb.utils.Utils
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Created by shekhar on 2019-09-07.
 */
class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var viewModel: MainViewModel
  private lateinit var moviesAdapter: MoviesAdapter
  private var gridLayoutManager: GridLayoutManager? = null


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    setUpRecyclerView()


    viewModel.moviesList.observe(this, Observer<ArrayList<Movie>> {
      moviesAdapter.resetAdapter(it)
      moviesAdapter.notifyDataSetChanged()
    })


    viewModel.isApiCallInProgress.observe(this, Observer<Boolean> {
      progressBar.visibility = if (it) View.VISIBLE else View.GONE
    })

    viewModel.noResultsFound.observe(this, Observer<Boolean> {
      noResultViewContainer.visibility = if (it) View.VISIBLE else View.GONE
      moviesAdapter.clearAdapter()
      moviesAdapter.notifyDataSetChanged()
    })

    viewModel.isError.observe(this, Observer<String> {
      if (it.isNullOrEmpty()) {
        errorViewContainer.visibility = View.GONE
      } else {
        errorViewContainer.errorTitleView.text = it
        errorViewContainer.visibility = View.VISIBLE
      }
    })

    errorViewContainer.actionRetry.setOnClickListener {
      callSearchApi("batman")
    }

    callSearchApi("batman")
  }

  private fun callSearchApi(searchedKey: String) {
    if (Utils.isNetworkConnected(activity!!))
      viewModel.callApi(searchedKey)
    else
      viewModel.noInternetConnection()
  }


  private fun setUpRecyclerView() {
    gridLayoutManager = GridLayoutManager(activity, 2)
    moviesRV.layoutManager = gridLayoutManager
    moviesAdapter = MoviesAdapter()
    moviesRV.adapter = moviesAdapter

//    TODO: For pagination
//    moviesRV.addOnScrollListener(scrollListener)
  }


  private var scrollListener: RecyclerView.OnScrollListener =
    object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
//          val visibleItemCount = gridLayoutManager?.childCount!!
//          val totalItemCount = gridLayoutManager?.itemCount!!
//          val pastVisibleItems = gridLayoutManager?.findFirstVisibleItemPosition()!!
//          if (visibleItemCount + pastVisibleItems >= totalItemCount && (pageNumber * 10) <= totalItemCount) {
////          TODO increment page and call api
//          }
        }
      }
    }


  //  START: SEARCH
  private var searchView: SearchView? = null
  private var queryTextListener: SearchView.OnQueryTextListener? = null

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_search ->
        // Not implemented here
        return false
      else -> {
      }
    }
    searchView?.setOnQueryTextListener(queryTextListener)
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu, menu)
    val searchItem = menu.findItem(R.id.action_search)
    val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

    if (searchItem != null) {
      searchView = searchItem.actionView as SearchView
    }
    if (searchView != null) {
      searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

      queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
          if (!p0.isNullOrEmpty()) {
            callSearchApi(p0)
            Utils.hideKeyboard(activity!!)
          }
          return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
          return true
        }

      }
      searchView?.setOnQueryTextListener(queryTextListener)
    }
    super.onCreateOptionsMenu(menu, inflater)
  }
  //  END: SEARCH


}
