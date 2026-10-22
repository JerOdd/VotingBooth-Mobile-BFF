package com.votingbooth.mobilebff.dataaccess.client.law;

import com.votingbooth.mobilebff.model.law.LawPage;
import com.votingbooth.mobilebff.model.law.LawStatus;

public interface LawClient {
    LawPage getLaws(int page, int size, LawStatus status, String category);
}
