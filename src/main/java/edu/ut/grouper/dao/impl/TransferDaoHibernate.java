package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("transferDao")
public class TransferDaoHibernate extends PageHibernateDaoSupport<Transfer> implements TransferDao {

    public TransferDaoHibernate() {
        super();
        setClass(Transfer.class);
    }

    public List<Transfer> findByReceiver(User user) {
        if (user == null) {
            return null;
        }
        String hql = "from Transfer where receiver = ?";
        return (List<Transfer>) getHibernateTemplate().find(hql, user);
    }

    public List<Transfer> findMulticastInGroup(Group group) {
        String hql = "from Transfer where sender.group = ? and receiver = null";
        return (List<Transfer>) getHibernateTemplate().find(hql, group);
    }

    public List<Transfer> findInTids(final List<String> tids) {
        final String hql = "from Transfer where tid in(:tids)";
        return (List<Transfer>) getHibernateTemplate().execute(new HibernateCallback<List<Transfer>>() {
            public List<Transfer> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameterList("tids", tids);
                return query.list();
            }
        });
    }

    public List<Transfer> findInMessageIds(final List<String> messageIds) {
        final String hql = "from Transfer where messageId in(:messageIds)";
        return (List<Transfer>) getHibernateTemplate().execute(new HibernateCallback<List<Transfer>>() {
            public List<Transfer> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameterList("messageIds", messageIds);
                return query.list();
            }
        });
    }

    public List<Transfer> findBeforeSaveTimeInGroup(Long savetime, Group group) {
        String hql = "from Transfer where savetime < ? and sender.group = ?";
        return (List<Transfer>) getHibernateTemplate().find(hql, savetime, group);
    }

    public Transfer getByMessageId(String messageId) {
        String hql = "from Transfer where messageId = ?";
        List<Transfer> transfers = (List<Transfer>) getHibernateTemplate().find(hql, messageId);
        if (transfers.size() == 0) {
            return null;
        }
        return transfers.get(0);
    }

}
