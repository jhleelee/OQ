package com.jackleeentertainment.oq.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.ui.layout.viewholder.PostViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 21..
 */

public class FeedRVAdapter extends RecyclerView.Adapter<PostViewHolder> {


    ArrayList<Post> postArrayList;

    public FeedRVAdapter() {
        super();
        postArrayList = new ArrayList<>();
    }

    /*
    Called when RecyclerView needs a new RecyclerView.
    ViewHolderReceipt of the given type to represent an item.
    This new ViewHolderReceipt should be constructed with a new View that can represent the items of
    the given type. You can either create a new View manually or inflate it from an XML
    layout file.
    The new ViewHolderReceipt will be used to display items of the adapter using
    onBindViewHolder(ViewHolderReceipt, int, List).
    Since it will be re-used to display different items in the data set, it is a good idea
    to cache references to sub views of the View to avoid unnecessary findViewById(int) calls.

    http://stackoverflow.com/questions/30615400/android-recyclerview-adapter-oncreateviewholder-working
    onCreateViewHolder only creates a new view holder when there are no existing view holders
    which the RecyclerView can reuse. So, for instance, if your RecyclerView can display 5 items
    at a time, it will create 5-6 ViewHolders, and then automatically reuse them, each time
    calling onBindViewHolder.
    Its similar to what your code in the ListView does (checking if convertView is null,
    and if not, grabbing the existing ViewHolderReceipt from the tag), except, with RecyclerView,
    this is all done automatically.
     */
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.i_feed, parent, false);
        return new PostViewHolder(itemView);
    }

    /*
    Called by RecyclerView to display the data at the specified position.
    This method should update the contents of the itemView to reflect the item at the given
    position.

    Note that unlike ListView, RecyclerView will not call this method again if the position of
    the item changes in the data set unless the item itself is invalidated or the new position
    cannot be determined. For this reason, you should only use the position parameter while
    acquiring the related data item inside this method and should not keep a copy of it.
    If you need the position of an item later on (e.g. in a click listener), use
    getAdapterPosition() which will have the updated adapter position. Override
    onBindViewHolder(ViewHolderReceipt, int, List) instead if Adapter can handle efficient partial bind.

    Partial bind vs full bind:

    The payloads parameter is a merge list from notifyItemChanged(int, Object) or
    notifyItemRangeChanged(int, int, Object). If the payloads list is not empty, the ViewHolderReceipt
    is currently bound to old data and Adapter may run an efficient partial update using
    the payload info. If the payload is empty, Adapter must run a full bind.
    Adapter should not assume that the payload passed in notify methods will be received by
    onBindViewHolder(). For example when the view is not attached to the screen, the payload
    in notifyItemChange() will be simply dropped.

    int: The position of the item within the adapter's data set.
    List: A non-null list of merged payloads. Can be empty list if requires full update.
     */

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = getPostArrayList().get(position);
        holder.setAlarmTitleAndContent(post);
        holder.setCommentNum(post.getComments().size());
        holder.setFavorite();
        holder.setLikeNum(post.getLike_uids().size());
        holder.setPostBackgroundMedia(post);
        holder.setPosterNameAndDeed(post.getName(),post.getPosttype());
        holder.setSharedNum(0);
        holder.setPosterPhoto(post.getUid());
        holder.setPostSupportingText(post.getTxt());
    }



    @Override
    public void onViewRecycled(PostViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(PostViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(PostViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(PostViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }



    //Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    //Return the stable ID for the item at position.
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /*
    Return the view type of the item at position for the purposes of view recycling.

    The default implementation of this method returns 0, making the assumption of a single
    view type for the adapter. Unlike ListView adapters, types need not be contiguous.
    Consider using id resources to uniquely identify
    */
    @Override
    public int getItemViewType(int position) {
        return 0;
    //original : return super.getItemViewType(position);
    }


    public ArrayList<Post> getPostArrayList() {
        return postArrayList;
    }

    public void setPostArrayList(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }
}
