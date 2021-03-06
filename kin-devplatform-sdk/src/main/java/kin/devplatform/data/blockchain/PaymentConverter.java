package kin.devplatform.data.blockchain;

import android.support.annotation.NonNull;
import kin.core.PaymentInfo;
import kin.devplatform.data.model.Payment;

public class PaymentConverter {

	public static Payment toPayment(@NonNull PaymentInfo paymentInfo, @NonNull String orderID,
		@NonNull String accountPublicAddress) {
		boolean isEarn = paymentInfo.destinationPublicKey().equals(accountPublicAddress);
		return new Payment(orderID, paymentInfo.hash().id(), paymentInfo.amount(),
			isEarn ? Payment.EARN : Payment.SPEND);
	}
}
