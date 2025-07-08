document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modal');
    const modalBody = modal ? modal.querySelector('.modal-body') : null;

    document.addEventListener('click', e => {
        const loadBtn = e.target.closest('.load-more');
        if (loadBtn) {
            const grid = document.querySelector(loadBtn.dataset.target);
            if (grid) {
                grid.querySelectorAll('.hidden').forEach(el => el.classList.remove('hidden'));
            }
            loadBtn.remove();
            return;
        }

        const closeBtn = e.target.closest('.close');
        if (closeBtn && modal) {
            modal.classList.remove('open');
            return;
        }

        if (modal && e.target === modal) {
            modal.classList.remove('open');
            return;
        }

        const card = e.target.closest('.card');
        if (card && modalBody) {
            const content = card.querySelector('.full-content').innerHTML;
            modalBody.innerHTML = content;
            modal.classList.add('open');
            modal.querySelector('.close').focus();
        }
    });

    document.addEventListener('keydown', e => {
        if (e.key === 'Escape' && modal) {
            modal.classList.remove('open');
        }
    });
});
