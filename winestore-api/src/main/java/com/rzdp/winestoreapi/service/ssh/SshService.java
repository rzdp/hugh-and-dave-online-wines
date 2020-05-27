package com.rzdp.winestoreapi.service.ssh;

import java.io.File;

public interface SshService {

    String uploadFile(File file, String remotePath);
}
