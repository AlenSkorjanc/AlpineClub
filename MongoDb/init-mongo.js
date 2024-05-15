db.createUser({
    user: 'admin',
    pwd: 'XIlHVPB71b7n',
    roles: [
        { role: 'readWrite', db: 'users' },
        { role: 'readWrite', db: 'articles' },
        { role: 'readWrite', db: 'events' }
    ]
});