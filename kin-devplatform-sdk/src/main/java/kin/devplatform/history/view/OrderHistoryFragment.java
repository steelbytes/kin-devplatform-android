package kin.devplatform.history.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import kin.devplatform.R;
import kin.devplatform.base.BaseRecyclerAdapter;
import kin.devplatform.base.BaseRecyclerAdapter.OnItemClickListener;
import kin.devplatform.history.presenter.ICouponDialogPresenter;
import kin.devplatform.history.presenter.IOrderHistoryPresenter;
import kin.devplatform.history.presenter.OrderHistoryPresenter;
import kin.devplatform.network.model.Order;

public class OrderHistoryFragment extends Fragment implements IOrderHistoryView {

	public static OrderHistoryFragment newInstance() {
		return new OrderHistoryFragment();
	}

	private IOrderHistoryPresenter orderHistoryPresenter;
	private OrderHistoryRecyclerAdapter orderHistoryRecyclerAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.kinecosystem_fragment_order_history, container, false);
		initViews(root);
		orderHistoryPresenter.onAttach(this);
		return root;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		orderHistoryPresenter.onDetach();
	}

	@Override
	public void onDestroy() {
		orderHistoryPresenter.onClose();
		orderHistoryPresenter = null;
		super.onDestroy();
	}

	protected void initViews(View root) {
		RecyclerView orderRecyclerView = root.findViewById(R.id.order_history_recycler);
		orderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		orderHistoryRecyclerAdapter = new OrderHistoryRecyclerAdapter();
		orderHistoryRecyclerAdapter.bindToRecyclerView(orderRecyclerView);
		orderHistoryRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(BaseRecyclerAdapter adapter, View view, int position) {
				orderHistoryPresenter.onItemCLicked(position);
			}
		});
	}

	@Override
	public void attachPresenter(OrderHistoryPresenter presenter) {
		orderHistoryPresenter = presenter;
	}

	@Override
	public void updateOrderHistoryList(List<Order> orders) {
		orderHistoryRecyclerAdapter.setNewData(orders);
		orderHistoryRecyclerAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemInserted() {
		orderHistoryRecyclerAdapter.notifyItemInserted(0);
	}

	@Override
	public void onItemUpdated(int index) {
		orderHistoryRecyclerAdapter.notifyItemChanged(index);
	}

	@Override
	public void showCouponDialog(@NonNull ICouponDialogPresenter presenter) {
		CouponDialog couponDialog = new CouponDialog(getActivity(), presenter);
		couponDialog.show();
	}
}
