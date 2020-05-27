package com.rzdp.winestoreapi.service.ssh.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.rzdp.winestoreapi.config.properties.SshProperties;
import com.rzdp.winestoreapi.exception.SshException;
import com.rzdp.winestoreapi.service.ssh.SshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class SshServiceImpl implements SshService {

    private static final String SFTP_CHANNEL_TYPE = "sftp";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SshProperties sshProperties;

    @Autowired
    public SshServiceImpl(SshProperties sshProperties) {
        this.sshProperties = sshProperties;
    }

    @Override
    public String uploadFile(File localFile, String remoteFile) {

        Session session = null;
        Channel channel = null;
        ChannelSftp sftp = null;
        InputStream is = null;

        try {
            log.info("Creating a session to connect to SSH remote server");

            JSch ssh = new JSch();
            String username = sshProperties.getUsername();
            String host = sshProperties.getHost();
            int port = sshProperties.getPort();

            // Create SSH Session
            session = ssh.getSession(username, host, port);
            session.setPassword(sshProperties.getPassword());

            // Setup properties
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            // Connect Session
            log.info("[{}] successfully created a session with [{}]", username, host + ":" + port);

            // Create Channel
            log.info("Opening session's {} channel", SFTP_CHANNEL_TYPE);
            channel = session.openChannel("sftp");
            log.info("Session's {} channel opened successfully", SFTP_CHANNEL_TYPE);

            log.info("Connecting to SSH remote server");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("Connecting to SSH remote server successful");

            // Upload File
            log.info("Uploading file to path {}", remoteFile);
            is = new FileInputStream(localFile);
            sftp.put(is, remoteFile);
            log.info("Uploading {} successful!", remoteFile);
            return remoteFile;
        } catch (JSchException | SftpException | IOException e) {
            throw new SshException("Unable to upload file in remote server " + e);
        } finally {
            // Disconnect
            log.info("Disconnecting from SSH remote server");
            sftp.exit();
            channel.disconnect();
            session.disconnect();
            try {
                is.close();
            } catch (IOException e) {
                log.error("ERROR: Unable to close file connection {}", e);
            }
            log.info("SSH remote server connection disconnected");
        }

    }
}
